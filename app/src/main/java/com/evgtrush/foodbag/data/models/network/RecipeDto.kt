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
package com.evgtrush.foodbag.data.models.network

data class RecipeDto(
    val name: String = "",
    val type: String = "",
    val previewImageUrl: String = "",
    val imageUrl: String = "",
    val author: String = "",
    val dateAdded: String = "",
    val description: String = "",
    val complexity: Int = 0,
    val rating: Int = 0,
    val ingredients: List<RecipeIngredientDto> = emptyList(),
    val steps: List<RecipeStepDto> = emptyList(),
)

data class RecipeIngredientDto(
    val name: String = "",
    val qty: String = "",
)

data class RecipeStepDto(
    val image: String = "",
    val text: String = "",
)