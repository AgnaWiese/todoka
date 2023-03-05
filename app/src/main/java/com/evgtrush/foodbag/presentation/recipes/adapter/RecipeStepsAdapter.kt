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

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evgtrush.foodbag.databinding.ListItemCookingStepBinding
import com.evgtrush.foodbag.domain.models.RecipeStep

internal class RecipeStepsAdapter(private val steps: List<RecipeStep>) :
    RecyclerView.Adapter<RecipeStepsAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            ListItemCookingStepBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
        holder.bindView(steps[position], position)

    override fun getItemCount(): Int = steps.size

    internal class RecipeViewHolder(private val binding: ListItemCookingStepBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindView(step: RecipeStep, position: Int) {
            with(binding) {
                stepText.text = "${position+1}. ${step.text}"
                if (step.imageUrl.isEmpty()) {
                    image.visibility = View.GONE
                } else {
                    image.visibility = View.VISIBLE
                    Glide
                        .with(root.context)
                        .load(step.imageUrl)
                        .into(image)
                }

            }
        }
    }
}