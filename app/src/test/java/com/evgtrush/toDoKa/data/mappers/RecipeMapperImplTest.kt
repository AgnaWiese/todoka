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
package com.evgtrush.toDoKa.data.mappers

import com.evgtrush.toDoKa.data.models.network.RecipeDto
import com.evgtrush.toDoKa.data.models.network.RecipeIngredientDto
import com.evgtrush.toDoKa.data.models.network.RecipeStepDto
import com.evgtrush.toDoKa.domain.models.Recipe
import com.evgtrush.toDoKa.domain.models.RecipeIngredient
import com.evgtrush.toDoKa.domain.models.RecipeStep
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class RecipeMapperImplTest {

    private lateinit var mapper: RecipeMapper
    private lateinit var recipeDto: RecipeDto
    private lateinit var recipe: Recipe

    @Before
    fun setUp() {
        mapper = RecipeMapperImpl()
        recipeDto =
            RecipeDto(
                name = "Test recipe",
                type = "main dish",
                previewImageUrl = "https://test_preview.png",
                imageUrl = "https://test.png",
                author = "ToDoKa",
                dateAdded = "01.01.2023",
                description = "Just open a fridge",
                complexity = 1,
                rating = 3,
                ingredients = listOf(
                    RecipeIngredientDto(
                        name = "Carrot",
                        qty = "1 kg."
                    )
                ),
                steps = listOf(
                    RecipeStepDto(
                        image = "https://test2.png",
                        text = "Make everything cool"
                    )
                )
            )
        recipe =
            Recipe(
                name = "Test recipe",
                type = "main dish",
                previewImageUrl = "https://test_preview.png",
                imageUrl = "https://test.png",
                author = "ToDoKa",
                dateAdded = "01.01.2023",
                description = "Just open a fridge",
                complexity = 1,
                rating = 3,
                ingredients = listOf(
                    RecipeIngredient(
                        name = "Carrot",
                        qty = "1 kg."
                    )
                ),
                steps = listOf(
                    RecipeStep(
                        imageUrl = "https://test2.png",
                        text = "Make everything cool"
                    )
                )
            )
    }

    @Test
    fun `convert() - data model is converted correctly to domain model`() {
        runBlocking {
            val result = mapper.convert(recipeDto)

            Truth.assertThat(result).isEqualTo(recipe)
        }
    }
}