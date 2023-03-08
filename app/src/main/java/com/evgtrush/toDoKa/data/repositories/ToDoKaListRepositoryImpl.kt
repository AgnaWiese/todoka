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

import com.evgtrush.toDoKa.data.datasources.db.ToDoKaItemDao
import com.evgtrush.toDoKa.data.datasources.db.ToDoKaListDao
import com.evgtrush.toDoKa.data.mappers.ToDoKaItemMapper
import com.evgtrush.toDoKa.data.mappers.ToDoKaListMapper
import com.evgtrush.toDoKa.domain.models.RecipeIngredient
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.evgtrush.toDoKa.domain.models.ToDoKaList
import com.evgtrush.toDoKa.domain.repositories.ToDoKaListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToDoKaListRepositoryImpl @Inject constructor(
    private val toDoKaListDao: ToDoKaListDao,
    private val toDoKaItemDao: ToDoKaItemDao,
    private val toDoKaListMapper: ToDoKaListMapper,
    private val toDoKaItemMapper: ToDoKaItemMapper,
    private val dispatcher: CoroutineDispatcher,
) : ToDoKaListRepository {

    override suspend fun getToDoKaLists(): List<ToDoKaList> = withContext(dispatcher) {
        toDoKaListDao.getAll().map {
            toDoKaListMapper.convert(it).apply {
                //TODO: refactor
                progress = (toDoKaItemDao.getBoughtItemsCountByToDoKaListId(it.uid) /
                        toDoKaItemDao.getItemsCountByToDoKaListId(it.uid).toFloat() * 100).toInt()
            }
        }
    }

    override suspend fun createToDoKaList(toDoKaList: ToDoKaList) {
        withContext(dispatcher) {
            toDoKaListDao.insertAll(toDoKaListMapper.reverse(toDoKaList))
        }
    }

    override suspend fun createToDoKaListByIngredients(
        toDoKaList: ToDoKaList,
        ingredients: List<RecipeIngredient>
    ) = withContext(dispatcher) {
        val ids = toDoKaListDao.insertAll(toDoKaListMapper.reverse(toDoKaList))
        //TODO: optimize
        for (ingredient in ingredients) {
            toDoKaItemDao.insertAll(toDoKaItemMapper.reverse(
                ToDoKaItem(
                    name = ingredient.name,
                    toDoKaListId = ids[0].toInt()
                )
            ))
        }
    }

    override suspend fun editToDoKaList(toDoKaList: ToDoKaList) = withContext(dispatcher) {
        toDoKaListDao.update(toDoKaListMapper.reverse(toDoKaList))
    }

    override suspend fun removeToDoKaList(toDoKaList: ToDoKaList) = withContext(dispatcher) {
        toDoKaListDao.delete(toDoKaListMapper.reverse(toDoKaList))
    }

    override suspend fun getToDoKaItems(toDoKaListId: Int): List<ToDoKaItem> = withContext(dispatcher) {
        toDoKaItemDao.getAllByToDoKaListId(toDoKaListId).map {
            toDoKaItemMapper.convert(it)
        }
    }

    override suspend fun addToDoKaItem(toDoKaItem: ToDoKaItem) = withContext(dispatcher) {
        toDoKaItemDao.insertAll(toDoKaItemMapper.reverse(toDoKaItem))
    }

    override suspend fun editToDoKaItem(toDoKaItem: ToDoKaItem) = withContext(dispatcher) {
        toDoKaItemDao.update(toDoKaItemMapper.reverse(toDoKaItem))
    }

    override suspend fun removeToDoKaItem(toDoKaItem: ToDoKaItem) = withContext(dispatcher) {
        toDoKaItemDao.delete(toDoKaItemMapper.reverse(toDoKaItem))
    }
}