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
package com.evgtrush.foodbag.domain.repositories

import com.evgtrush.foodbag.domain.models.RecipeIngredient
import com.evgtrush.foodbag.domain.models.ShoppingItem
import com.evgtrush.foodbag.domain.models.ShoppingList

interface ShoppingListRepository {

    suspend fun getShoppingLists(): List<ShoppingList>

    suspend fun createShoppingList(shoppingList: ShoppingList)

    suspend fun createShoppingListByIngredients(shoppingList: ShoppingList, ingredients: List<RecipeIngredient>)

    suspend fun editShoppingList(shoppingList: ShoppingList)

    suspend fun removeShoppingList(shoppingList: ShoppingList)

    suspend fun getShoppingItems(shoppingListId: Int): List<ShoppingItem>

    suspend fun addShoppingItem(shoppingItem: ShoppingItem)

    suspend fun editShoppingItem(shoppingItem: ShoppingItem)

    suspend fun removeShoppingItem(shoppingItem: ShoppingItem)
}