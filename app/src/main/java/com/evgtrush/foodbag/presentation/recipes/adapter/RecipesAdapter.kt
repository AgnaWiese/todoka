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
package com.evgtrush.foodbag.presentation.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evgtrush.foodbag.databinding.ListItemRecipeBinding
import com.evgtrush.foodbag.domain.models.Recipe
import com.evgtrush.foodbag.presentation.recipes.RecipesFragmentDirections

internal class RecipesAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            ListItemRecipeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
        holder.bindView(recipes[position])

    override fun getItemCount(): Int = recipes.size

    internal class RecipeViewHolder(private val binding: ListItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(recipe: Recipe) {
            with(binding) {
                headline.text = recipe.name
                rating.rating = recipe.rating.toFloat()
                supportingText.text = recipe.description
                Glide
                    .with(root.context)
                    .load(recipe.imageUrl)
                    .into(recipeImage)
                materialCardContainer.setOnClickListener {
                    val direction = RecipesFragmentDirections.actionOpenRecipe(recipe)
                    Navigation.findNavController(it).navigate(direction)
                }
            }
        }
    }
}