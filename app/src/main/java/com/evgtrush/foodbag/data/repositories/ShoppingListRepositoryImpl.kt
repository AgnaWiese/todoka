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
package com.evgtrush.foodbag.data.repositories

import com.evgtrush.foodbag.data.datasources.db.ShoppingItemDao
import com.evgtrush.foodbag.data.datasources.db.ShoppingListDao
import com.evgtrush.foodbag.data.mappers.ShoppingItemMapper
import com.evgtrush.foodbag.data.mappers.ShoppingListMapper
import com.evgtrush.foodbag.domain.models.RecipeIngredient
import com.evgtrush.foodbag.domain.models.ShoppingItem
import com.evgtrush.foodbag.domain.models.ShoppingList
import com.evgtrush.foodbag.domain.repositories.ShoppingListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
    private val shoppingItemDao: ShoppingItemDao,
    private val shoppingListMapper: ShoppingListMapper,
    private val shoppingItemMapper: ShoppingItemMapper,
    private val dispatcher: CoroutineDispatcher,
) : ShoppingListRepository {

    override suspend fun getShoppingLists(): List<ShoppingList> = withContext(dispatcher) {
        shoppingListDao.getAll().map {
            shoppingListMapper.convert(it).apply {
                //TODO: refactor
                progress = (shoppingItemDao.getBoughtItemsCountByShoppingListId(it.uid) /
                        shoppingItemDao.getItemsCountByShoppingListId(it.uid).toFloat() * 100).toInt()
            }
        }
    }

    override suspend fun createShoppingList(shoppingList: ShoppingList) {
        withContext(dispatcher) {
            shoppingListDao.insertAll(shoppingListMapper.reverse(shoppingList))
        }
    }

    override suspend fun createShoppingListByIngredients(
        shoppingList: ShoppingList,
        ingredients: List<RecipeIngredient>
    ) = withContext(dispatcher) {
        val ids = shoppingListDao.insertAll(shoppingListMapper.reverse(shoppingList))
        //TODO: optimize
        for (ingredient in ingredients) {
            shoppingItemDao.insertAll(shoppingItemMapper.reverse(
                ShoppingItem(
                    name = ingredient.name,
                    shoppingListId = ids[0].toInt()
                )
            ))
        }
    }

    override suspend fun editShoppingList(shoppingList: ShoppingList) = withContext(dispatcher) {
        shoppingListDao.update(shoppingListMapper.reverse(shoppingList))
    }

    override suspend fun removeShoppingList(shoppingList: ShoppingList) = withContext(dispatcher) {
        shoppingListDao.delete(shoppingListMapper.reverse(shoppingList))
    }

    override suspend fun getShoppingItems(shoppingListId: Int): List<ShoppingItem> = withContext(dispatcher) {
        shoppingItemDao.getAllByShoppingListId(shoppingListId).map {
            shoppingItemMapper.convert(it)
        }
    }

    override suspend fun addShoppingItem(shoppingItem: ShoppingItem) = withContext(dispatcher) {
        shoppingItemDao.insertAll(shoppingItemMapper.reverse(shoppingItem))
    }

    override suspend fun editShoppingItem(shoppingItem: ShoppingItem) = withContext(dispatcher) {
        shoppingItemDao.update(shoppingItemMapper.reverse(shoppingItem))
    }

    override suspend fun removeShoppingItem(shoppingItem: ShoppingItem) = withContext(dispatcher) {
        shoppingItemDao.delete(shoppingItemMapper.reverse(shoppingItem))
    }
}