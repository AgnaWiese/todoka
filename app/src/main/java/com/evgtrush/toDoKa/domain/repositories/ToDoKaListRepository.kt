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
package com.evgtrush.toDoKa.domain.repositories

import com.evgtrush.toDoKa.domain.models.RecipeIngredient
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.evgtrush.toDoKa.domain.models.ToDoKaList

interface ToDoKaListRepository {

    suspend fun getToDoKaLists(): List<ToDoKaList>

    suspend fun createToDoKaList(shoppingList: ToDoKaList)

    suspend fun createToDoKaListByIngredients(shoppingList: ToDoKaList, ingredients: List<RecipeIngredient>)

    suspend fun editToDoKaList(shoppingList: ToDoKaList)

    suspend fun removeToDoKaList(shoppingList: ToDoKaList)

    suspend fun getToDoKaItems(shoppingListId: Int): List<ToDoKaItem>

    suspend fun addToDoKaItem(shoppingItem: ToDoKaItem)

    suspend fun editToDoKaItem(shoppingItem: ToDoKaItem)

    suspend fun removeToDoKaItem(shoppingItem: ToDoKaItem)
}