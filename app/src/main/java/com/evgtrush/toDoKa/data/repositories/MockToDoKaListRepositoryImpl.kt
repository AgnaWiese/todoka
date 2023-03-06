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

import com.evgtrush.toDoKa.domain.models.RecipeIngredient
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.evgtrush.toDoKa.domain.models.ToDoKaList
import com.evgtrush.toDoKa.domain.repositories.ToDoKaListRepository
import javax.inject.Inject

class MockToDoKaListRepositoryImpl @Inject constructor() : ToDoKaListRepository {

    override suspend fun getToDoKaLists(): List<ToDoKaList> = listOf(
        ToDoKaList(1, "Булочная", 0),
        ToDoKaList(1, "Продукты", 25),
        ToDoKaList(2, "Аптека", 75),
        ToDoKaList(3, "Рататуй", 100),
    )

    override suspend fun createToDoKaList(toDoKaList: ToDoKaList) {
    }

    override suspend fun createToDoKaListByIngredients(
        toDoKaList: ToDoKaList,
        ingredients: List<RecipeIngredient>
    ) {
    }

    override suspend fun editToDoKaList(toDoKaList: ToDoKaList) {
    }

    override suspend fun removeToDoKaList(toDoKaList: ToDoKaList) {
    }

    override suspend fun getToDoKaItems(toDoKaListId: Int): List<ToDoKaItem> = listOf(
        ToDoKaItem(0, "Картофель", true),
        ToDoKaItem(1, "Яйца"),
        ToDoKaItem(2,"Лук"),
        ToDoKaItem(3,"Молоко"),
    )

    override suspend fun addToDoKaItemItem(toDoKaItem: ToDoKaItem) {
    }

    override suspend fun editToDoKaItem(toDoKaItem: ToDoKaItem) {
    }

    override suspend fun removeToDoKaItem(toDoKaItem: ToDoKaItem) {
    }
}