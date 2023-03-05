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
package com.evgtrush.foodbag.data.mappers

import com.evgtrush.foodbag.data.models.network.RecipeDto
import com.evgtrush.foodbag.domain.models.Recipe
import com.evgtrush.foodbag.domain.models.RecipeIngredient
import com.evgtrush.foodbag.domain.models.RecipeStep
import javax.inject.Inject

class RecipeMapperImpl @Inject constructor(): RecipeMapper {

    override fun convert(dto: RecipeDto): Recipe =
        Recipe(
            name = dto.name,
            type = dto.type,
            previewImageUrl = dto.previewImageUrl,
            imageUrl = dto.imageUrl,
            author = dto.author,
            dateAdded = dto.dateAdded,
            description = dto.description,
            complexity = dto.complexity,
            rating = dto.rating,
            ingredients = dto.ingredients.map {
                RecipeIngredient(
                    name = it.name,
                    qty = it.qty
                )
            },
            steps = dto.steps.map {
                RecipeStep(
                    imageUrl = it.image,
                    text = it.text
                )
            }
        )
}