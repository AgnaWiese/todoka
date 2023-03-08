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
package com.evgtrush.toDoKa.presentation.recipes

import com.evgtrush.toDoKa.domain.interactors.RecipeInteractor
import com.evgtrush.toDoKa.domain.models.Recipe
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
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
class RecipesViewModelTest {

    @Mock
    private lateinit var interactor: RecipeInteractor

    private lateinit var viewModel: RecipesViewModel
    private lateinit var recipes: List<Recipe>
    private lateinit var uiState: RecipesViewModel.RecipesUiState

    @Before
    fun setUp() {
        viewModel = RecipesViewModel(interactor)
        recipes = listOf(
            Recipe(
                name = "Test Recipe",
                type = "main dish",
                author = "toDoKa"
            )
        )
    }

    @Test
    fun `getRecipes() - calls interactor and returns recipes`() = runTest {
        whenever(interactor.getRecipes()).thenReturn(recipes)

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try {
            viewModel.getRecipes()

            Mockito.verify(interactor, times(1)).getRecipes()
            Mockito.verifyNoMoreInteractions(interactor)

            assertThat(viewModel.uiState.value).isEqualTo(
                RecipesViewModel.RecipesUiState(
                    isError = false,
                    recipes = recipes
                )
            )
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `getRecipes() - calls interactor and returns error state`() = runTest {
        whenever(interactor.getRecipes()).thenThrow(RuntimeException())

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try {
            viewModel.getRecipes()

            Mockito.verify(interactor, times(1)).getRecipes()
            Mockito.verifyNoMoreInteractions(interactor)

            assertThat(viewModel.uiState.value).isEqualTo(
                RecipesViewModel.RecipesUiState(
                    isError = true
                )
            )
        } finally {
            Dispatchers.resetMain()
        }
    }
}