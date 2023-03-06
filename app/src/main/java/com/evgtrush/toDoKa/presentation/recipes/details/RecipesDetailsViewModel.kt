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
package com.evgtrush.toDoKa.presentation.recipes.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgtrush.toDoKa.domain.interactors.ToDoKaListInteractor
import com.evgtrush.toDoKa.domain.models.RecipeIngredient
import com.evgtrush.toDoKa.domain.models.ToDoKaList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesDetailsViewModel @Inject constructor(
    private val interactor: ToDoKaListInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesDetailsUiState())
    val uiState: StateFlow<RecipesDetailsUiState> = _uiState.asStateFlow()

    fun createToDoKaListByIngredients(shoppingListName: String, ingredients: List<RecipeIngredient>) {
        viewModelScope.launch {
            try {
                interactor.createToDoKaListByIngredients(
                    shoppingList = ToDoKaList(
                        name = shoppingListName
                    ),
                    ingredients = ingredients
                )
                _uiState.update {
                    it.copy(
                        showCreateToDoKaListMessageOK = true
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
                showCreateToDoKaListMessageOK = false,
                isError = false
            )
        }
    }

    data class RecipesDetailsUiState(
        val showCreateToDoKaListMessageOK: Boolean = false,
        val isError: Boolean = false
    )
}