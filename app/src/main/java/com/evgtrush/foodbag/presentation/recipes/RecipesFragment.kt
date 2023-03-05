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
package com.evgtrush.foodbag.presentation.recipes

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
import com.evgtrush.foodbag.R
import com.evgtrush.foodbag.databinding.FragmentRecipesBinding
import com.evgtrush.foodbag.presentation.recipes.adapter.RecipesAdapter
import com.evgtrush.foodbag.presentation.utils.showBottomNav
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val viewModel: RecipesViewModel by viewModels()

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRecipesAsync()

        showBottomNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRecipesAsync() {
        binding.progress.visibility = View.VISIBLE
        viewModel.getRecipes()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    Log.d("RecipesFragment", "UI update: $it")

                    binding.recipesList.adapter = RecipesAdapter(it.recipes)

                    when {
                        it.isError -> showSnackBar(R.string.general_error)
                    }

                    binding.progress.visibility = View.GONE
                }
            }
        }
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.userMessageShown()
    }
}