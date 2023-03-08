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
package com.evgtrush.toDoKa.data.repositories

import com.evgtrush.toDoKa.data.datasources.network.NetworkRecipeDataSource
import com.evgtrush.toDoKa.data.mappers.RecipeMapperImpl
import com.evgtrush.toDoKa.data.models.network.RecipeDto
import com.evgtrush.toDoKa.domain.models.Recipe
import com.evgtrush.toDoKa.domain.repositories.RecipeRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RecipeRepositoryImplTest {

    @Mock
    private lateinit var dataSource: NetworkRecipeDataSource

    private lateinit var recipesDto: List<RecipeDto>
    private lateinit var recipes: List<Recipe>
    private lateinit var repository: RecipeRepository

    @Before
    fun setUp() {
        repository = RecipeRepositoryImpl(
            dataSource,
            RecipeMapperImpl(),
            Dispatchers.Default
        )
        recipesDto = listOf(
            RecipeDto(
                name = "Test Recipe",
                type = "main dish",
                author = "ToDoKa"
            )
        )
        recipes = listOf(
            Recipe(
                name = "Test Recipe",
                type = "main dish",
                author = "ToDoKa"
            )
        )
    }

    @Test
    fun `getRecipes() - calls datasource and returns recipes`() = runTest {
        whenever(dataSource.getRecipes()).thenReturn(recipesDto)

        val result = repository.getRecipes()

        Mockito.verify(dataSource, times(1)).getRecipes()
        Mockito.verifyNoMoreInteractions(dataSource)

        assertThat(result).isEqualTo(recipes)
    }
}