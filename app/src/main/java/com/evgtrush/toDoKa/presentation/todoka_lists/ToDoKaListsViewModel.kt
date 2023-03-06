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
package com.evgtrush.toDoKa.presentation.todoka_lists

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evgtrush.toDoKa.domain.interactors.ToDoKaListInteractor
import com.evgtrush.toDoKa.domain.models.ToDoKaList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoKaListsViewModel @Inject constructor(
    private val interactor: ToDoKaListInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(ToDoKaListsUiState())
    val uiState: StateFlow<ToDoKaListsUiState> = _uiState.asStateFlow()

    fun getToDoKaLists() {
        viewModelScope.launch {
            try {
                val toDoKaLists = interactor.getToDoKaLists()
                _uiState.update {
                    it.copy(
                        toDoKaLists = toDoKaLists
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

    fun createToDoKaList(toDoKaList: ToDoKaList) {
        viewModelScope.launch {
            try {
                interactor.createToDoKaList(toDoKaList)
                //TODO : оптимизировать, чтобы не грузить весь список заново
                val toDoKaLists = interactor.getToDoKaLists()
                _uiState.update {
                    it.copy(
                        toDoKaLists = toDoKaLists
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

    fun removeToDoKaList(toDoKaList: ToDoKaList) {
        viewModelScope.launch {
            try {
                interactor.removeToDoKaList(toDoKaList)
                //TODO : оптимизировать, чтобы не грузить весь список заново
                val toDoKaLists = interactor.getToDoKaLists()
                _uiState.update {
                    it.copy(
                        toDoKaLists = toDoKaLists
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

    fun renameToDoKaList(toDoKaList: ToDoKaList) {
        viewModelScope.launch {
            try {
                interactor.editToDoKaList(toDoKaList)
                //TODO : оптимизировать, чтобы не грузить весь список заново
                val toDoKaLists = interactor.getToDoKaLists()
                _uiState.update {
                    it.copy(
                        toDoKaLists = toDoKaLists
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

    fun userMessageShown() {
        _uiState.update {
            it.copy(
                isError = false
            )
        }
    }

    data class ToDoKaListsUiState(
        val isError: Boolean = false,
        val toDoKaLists: List<ToDoKaList> = emptyList()
    )
}