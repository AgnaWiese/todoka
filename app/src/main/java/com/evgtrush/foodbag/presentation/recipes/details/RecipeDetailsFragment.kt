/*
 * Copyright (C) 2023. Evgenia Trushkina
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evgtrush.foodbag.presentation.recipes.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.evgtrush.foodbag.R
import com.evgtrush.foodbag.databinding.FragmentRecipeDetailsBinding
import com.evgtrush.foodbag.domain.models.Recipe
import com.evgtrush.foodbag.domain.models.RecipeIngredient
import com.evgtrush.foodbag.presentation.recipes.adapter.RecipeStepsAdapter
import com.evgtrush.foodbag.presentation.utils.hideBottomNav
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeDetailsFragment : Fragment() {

    private val args: RecipeDetailsFragmentArgs by navArgs()
    private val viewModel: RecipesDetailsViewModel by viewModels()

    private var _binding: FragmentRecipeDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBottomNav()

        val recipe = args.recipe
        with(binding) {
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener { findNavController().navigateUp() }
                title = recipe?.name ?: ""
            }
            recipe?.let {
                btnShare.setOnClickListener { shareRecipe(recipe) }

                Glide
                    .with(root.context)
                    .load(recipe.imageUrl)
                    .into(image)

                rating.rating = it.rating.toFloat()
                description.text = recipe.description

                if (recipe.ingredients.isEmpty()) {
                    ingredientsContainer.visibility = View.GONE
                } else {
                    ingredientsContainer.visibility = View.VISIBLE
                    ingredients.text = prepareIngredientsList(it.ingredients)
                    btnAddToShoppingList.setOnClickListener { addToShoppingList(recipe) }
                }

                if (recipe.steps.isEmpty()) {
                    cookingStepsContainer.visibility = View.GONE
                } else {
                    cookingStepsList.adapter = RecipeStepsAdapter(recipe.steps)
                    cookingStepsContainer.visibility = View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    Log.d("RecipesDetailsFragment", "UI update: $it")

                    when {
                        it.showCreateShoppingListMessageOK -> {
                            showSnackBar(R.string.added_to_shopping_list)
                            binding.btnAddToShoppingList.isEnabled = false
                        }
                        it.isError -> showSnackBar(R.string.general_error)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.userMessageShown()
    }

    private fun shareRecipe(recipe: Recipe) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, recipeToString(recipe))
        }

        startActivity(Intent.createChooser(intent, getString(R.string.share)))
    }

    private fun addToShoppingList(recipe: Recipe) {
        if (recipe.ingredients.isNotEmpty()) {
            viewModel.createShoppingListByIngredients(recipe.name, recipe.ingredients)
        }
    }

    private fun recipeToString(recipe: Recipe): String =
        """
                Recipe: ${recipe.name},
                Description: ${recipe.description} ,
                Ingredients: ${prepareIngredientsList(recipe.ingredients)} 
        """.trimIndent()

    private fun prepareIngredientsList(ingredients: List<RecipeIngredient>): String =
        ingredients.joinToString(
            separator = "\n",
            transform = {
                if (it.qty.isEmpty()) {
                    "– ${it.name}"
                } else {
                    "– ${it.name}: ${it.qty}"
                }
            }
        )
}