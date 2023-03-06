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
package com.evgtrush.toDoKa.presentation.todoka_lists.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgtrush.toDoKa.domain.interactors.ToDoKaListInteractor
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoKaListDetailsViewModel @Inject constructor(
    private val interactor: ToDoKaListInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ToDoKaListDetailsUiState())
    val uiState: StateFlow<ToDoKaListDetailsUiState> = _uiState.asStateFlow()

    fun getToDoKaListItem(id: Int) {
        viewModelScope.launch {
            try {
                val toDoKaItems = interactor.getToDoKaItems(id)
                _uiState.update {
                    it.copy(
                        toDoKaItems = toDoKaItems
                    )
                }
            } catch (e: Exception) {
                Log.e("ToDoKaListDetailsViewModel", e.message ?: "Something went wrong...")
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun addToDoKaItem(toDoKaItem: ToDoKaItem) {
        viewModelScope.launch {
            try {
                interactor.addToDoKaItem(toDoKaItem)
                // TODO: Optimize
                val toDoKaItems = interactor.getToDoKaItems(toDoKaItem.toDoKaListId)
                _uiState.update {
                    it.copy(
                        toDoKaItems = toDoKaItems
                    )
                }
            } catch (e: Exception) {
                Log.e("ToDoKaListsViewModel", e.message ?: "Something went wrong...")
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun removeToDoKaItem(toDoKaItem: ToDoKaItem) {
        viewModelScope.launch {
            try {
                interactor.removeToDoKaItem(toDoKaItem)
                // TODO: Optimize
                val toDoKaItems = interactor.getToDoKaItems(toDoKaItem.toDoKaListId)
                _uiState.update {
                    it.copy(
                        toDoKaItems = toDoKaItems
                    )
                }
            } catch (e: Exception) {
                Log.e("ToDoKaListDetailsViewModel", e.message ?: "Something went wrong...")
                _uiState.update {
                    it.copy(
                        isError = true
                    )
                }
            }
        }
    }

    fun editToDoKaItem(toDoKaItem: ToDoKaItem) {
        viewModelScope.launch {
            try {
                interactor.editToDoKaItem(toDoKaItem)
                // TODO: Optimize
                val toDoKaItems = interactor.getToDoKaItems(toDoKaItem.toDoKaListId)
                _uiState.update {
                    it.copy(
                        toDoKaItems = toDoKaItems
                    )
                }
            } catch (e: Exception) {
                Log.e("ToDoKaListDetailsViewModel", e.message ?: "Something went wrong...")
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

    data class ToDoKaListDetailsUiState(
        val isError: Boolean = false,
        val toDoKaItems: List<ToDoKaItem> = emptyList()
    )
}