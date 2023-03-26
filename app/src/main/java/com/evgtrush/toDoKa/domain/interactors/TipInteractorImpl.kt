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

import com.evgtrush.toDoKa.domain.models.Tip
import com.evgtrush.toDoKa.domain.models.TipToDo
import com.evgtrush.toDoKa.domain.models.ToDoKaList
import com.evgtrush.toDoKa.domain.repositories.TipRepository
import javax.inject.Inject

class TipInteractorImpl @Inject constructor(
    private val repository: TipRepository
): TipInteractor {

    override suspend fun getTip(): List<Tip> = repository.getTips()

    override suspend fun createFavoriteTip(toDoKaList: ToDoKaList, toDo: List<TipToDo>) =
        repository.createFavoriteTip(toDoKaList, toDo)

    override suspend fun deleteFavoriteTip(toDoKaList: ToDoKaList,
                                           toDo: List<TipToDo>)  =
        repository.deleteFavoriteTip(toDoKaList, toDo)
}