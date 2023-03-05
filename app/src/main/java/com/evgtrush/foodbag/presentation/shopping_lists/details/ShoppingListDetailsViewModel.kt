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
package com.evgtrush.foodbag.presentation.shopping_lists.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgtrush.foodbag.domain.interactors.ShoppingListInteractor
import com.evgtrush.foodbag.domain.models.ShoppingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListDetailsViewModel @Inject constructor(
    private val interactor: ShoppingListInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListDetailsUiState())
    val uiState: StateFlow<ShoppingListDetailsUiState> = _uiState.asStateFlow()

    fun getShoppingListItem(id: Int) {
        viewModelScope.launch {
            try {
                val shoppingItems = interactor.getShoppingItems(id)
                _uiState.update {
                    it.copy(
                        shoppingItems = shoppingItems
                    )
                }
            } catch (e: Exception) {
                Log.e("ShoppingListDetailsViewModel", e.message ?: "Something went wrong...")
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun addShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            try {
                interactor.addShoppingItem(shoppingItem)
                // TODO: Optimize
                val shoppingItems = interactor.getShoppingItems(shoppingItem.shoppingListId)
                _uiState.update {
                    it.copy(
                        shoppingItems = shoppingItems
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

    fun removeShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            try {
                interactor.removeShoppingItem(shoppingItem)
                // TODO: Optimize
                val shoppingItems = interactor.getShoppingItems(shoppingItem.shoppingListId)
                _uiState.update {
                    it.copy(
                        shoppingItems = shoppingItems
                    )
                }
            } catch (e: Exception) {
                Log.e("ShoppingListDetailsViewModel", e.message ?: "Something went wrong...")
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun editShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            try {
                interactor.editShoppingItem(shoppingItem)
                // TODO: Optimize
                val shoppingItems = interactor.getShoppingItems(shoppingItem.shoppingListId)
                _uiState.update {
                    it.copy(
                        shoppingItems = shoppingItems
                    )
                }
            } catch (e: Exception) {
                Log.e("ShoppingListDetailsViewModel", e.message ?: "Something went wrong...")
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

    data class ShoppingListDetailsUiState(
        val isError: Boolean = false,
        val shoppingItems: List<ShoppingItem> = emptyList()
    )
}