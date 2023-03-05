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

import com.evgtrush.foodbag.domain.models.RecipeIngredient
import com.evgtrush.foodbag.domain.models.ShoppingItem
import com.evgtrush.foodbag.domain.models.ShoppingList
import com.evgtrush.foodbag.domain.repositories.ShoppingListRepository
import javax.inject.Inject

class MockShoppingListRepositoryImpl @Inject constructor() : ShoppingListRepository {

    override suspend fun getShoppingLists(): List<ShoppingList> = listOf(
        ShoppingList(1, "Булочная", 0),
        ShoppingList(1, "Продукты", 25),
        ShoppingList(2, "Аптека", 75),
        ShoppingList(3, "Рататуй", 100),
    )

    override suspend fun createShoppingList(shoppingList: ShoppingList) {
    }

    override suspend fun createShoppingListByIngredients(
        shoppingList: ShoppingList,
        ingredients: List<RecipeIngredient>
    ) {
    }

    override suspend fun editShoppingList(shoppingList: ShoppingList) {
    }

    override suspend fun removeShoppingList(shoppingList: ShoppingList) {
    }

    override suspend fun getShoppingItems(shoppingListId: Int): List<ShoppingItem> = listOf(
        ShoppingItem(0, "Картофель", true),
        ShoppingItem(1, "Яйца"),
        ShoppingItem(2,"Лук"),
        ShoppingItem(3,"Молоко"),
    )

    override suspend fun addShoppingItem(shoppingItem: ShoppingItem) {
    }

    override suspend fun editShoppingItem(shoppingItem: ShoppingItem) {
    }

    override suspend fun removeShoppingItem(shoppingItem: ShoppingItem) {
    }
}