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
package com.evgtrush.foodbag.presentation.shopping_lists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgtrush.foodbag.domain.interactors.ShoppingListInteractor
import com.evgtrush.foodbag.domain.models.ShoppingList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListsViewModel @Inject constructor(
    private val interactor: ShoppingListInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListsUiState())
    val uiState: StateFlow<ShoppingListsUiState> = _uiState.asStateFlow()

    fun getShoppingLists() {
        viewModelScope.launch {
            try {
                val shoppingLists = interactor.getShoppingLists()
                _uiState.update {
                    it.copy(
                        shoppingLists = shoppingLists
                    )
                }
            } catch (e: Exception) {
                Log.e("ShoppingListsViewModel", e.message ?: "Something went wrong...")
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun createShoppingList(shoppingList: ShoppingList) {
        viewModelScope.launch {
            try {
                interactor.createShoppingList(shoppingList)
                //TODO : оптимизировать, чтобы не грузить весь список заново
                val shoppingLists = interactor.getShoppingLists()
                _uiState.update {
                    it.copy(
                        shoppingLists = shoppingLists
                    )
                }
            } catch (e: Exception) {
                Log.e("ShoppingListsViewModel", e.message ?: "Something went wrong...")
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun removeShoppingList(shoppingList: ShoppingList) {
        viewModelScope.launch {
            try {
                interactor.removeShoppingList(shoppingList)
                //TODO : оптимизировать, чтобы не грузить весь список заново
                val shoppingLists = interactor.getShoppingLists()
                _uiState.update {
                    it.copy(
                        shoppingLists = shoppingLists
                    )
                }
            } catch (e: Exception) {
                Log.e("ShoppingListsViewModel", e.message ?: "Something went wrong...")
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun renameShoppingList(shoppingList: ShoppingList) {
        viewModelScope.launch {
            try {
                interactor.editShoppingList(shoppingList)
                //TODO : оптимизировать, чтобы не грузить весь список заново
                val shoppingLists = interactor.getShoppingLists()
                _uiState.update {
                    it.copy(
                        shoppingLists = shoppingLists
                    )
                }
            } catch (e: Exception) {
                Log.e("ShoppingListsViewModel", e.message ?: "Something went wrong...")
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
                isError = false
            )
        }
    }

    data class ShoppingListsUiState(
        val isError: Boolean = false,
        val shoppingLists: List<ShoppingList> = emptyList()
    )
}