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
package com.evgtrush.toDoKa.domain.interactors

import com.evgtrush.toDoKa.domain.models.TipToDo
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.evgtrush.toDoKa.domain.repositories.ToDoKaListRepository
import javax.inject.Inject
import com.evgtrush.toDoKa.domain.models.ToDoKaList as ToDoKaList1

class ToDoKaListInteractorImpl @Inject constructor(
    private val toDoKaListRepository: ToDoKaListRepository
) : ToDoKaListInteractor {

    override suspend fun getToDoKaLists(): List<ToDoKaList1> =
        toDoKaListRepository.getToDoKaLists()

    override suspend fun createToDoKaList(toDoKaList: ToDoKaList1) =
        toDoKaListRepository.createToDoKaList(toDoKaList)

    override suspend fun createToDoKaListByToDo(toDoKaList: ToDoKaList1,
                                                toDo: List<TipToDo>) =
        toDoKaListRepository.createToDoKaListByToDo(toDoKaList, toDo)

    override suspend fun editToDoKaList(toDoKaList: ToDoKaList1) =
        toDoKaListRepository.editToDoKaList(toDoKaList)

    override suspend fun removeToDoKaList(toDoKaList: ToDoKaList1) =
        toDoKaListRepository.removeToDoKaList(toDoKaList)

    override suspend fun getToDoKaItems(toDoKaListListId: Int): List<ToDoKaItem> =
        toDoKaListRepository.getToDoKaItems(toDoKaListListId)

    override suspend fun addToDoKaItem(toDoKaItem: ToDoKaItem) =
        toDoKaListRepository.addToDoKaItem(toDoKaItem)

    override suspend fun editToDoKaItem(toDoKaItem: ToDoKaItem) =
        toDoKaListRepository.editToDoKaItem(toDoKaItem)

    override suspend fun removeToDoKaItem(toDoKaItem: ToDoKaItem) =
        toDoKaListRepository.removeToDoKaItem(toDoKaItem)
}