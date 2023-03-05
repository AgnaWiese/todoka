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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgtrush.foodbag.domain.interactors.ShoppingListInteractor
import com.evgtrush.foodbag.domain.models.RecipeIngredient
import com.evgtrush.foodbag.domain.models.ShoppingList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesDetailsViewModel @Inject constructor(
    private val interactor: ShoppingListInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesDetailsUiState())
    val uiState: StateFlow<RecipesDetailsUiState> = _uiState.asStateFlow()

    fun createShoppingListByIngredients(shoppingListName: String, ingredients: List<RecipeIngredient>) {
        viewModelScope.launch {
            try {
                interactor.createShoppingListByIngredients(
                    shoppingList = ShoppingList(
                        name = shoppingListName
                    ),
                    ingredients = ingredients
                )
                _uiState.update {
                    it.copy(
                        showCreateShoppingListMessageOK = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update {
            it.copy(
                showCreateShoppingListMessageOK = false,
                isError = false
            )
        }
    }

    data class RecipesDetailsUiState(
        val showCreateShoppingListMessageOK: Boolean = false,
        val isError: Boolean = false
    )
}