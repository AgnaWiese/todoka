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
package com.evgtrush.foodbag.domain.interactors

import com.evgtrush.foodbag.domain.models.RecipeIngredient
import com.evgtrush.foodbag.domain.models.ShoppingItem
import com.evgtrush.foodbag.domain.models.ShoppingList
import com.evgtrush.foodbag.domain.repositories.ShoppingListRepository
import javax.inject.Inject

class ShoppingListInteractorImpl @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) : ShoppingListInteractor {

    override suspend fun getShoppingLists(): List<ShoppingList> =
        shoppingListRepository.getShoppingLists()

    override suspend fun createShoppingList(shoppingList: ShoppingList) =
        shoppingListRepository.createShoppingList(shoppingList)

    override suspend fun createShoppingListByIngredients(shoppingList: ShoppingList,
                                                         ingredients: List<RecipeIngredient>) =
        shoppingListRepository.createShoppingListByIngredients(shoppingList, ingredients)

    override suspend fun editShoppingList(shoppingList: ShoppingList) =
        shoppingListRepository.editShoppingList(shoppingList)

    override suspend fun removeShoppingList(shoppingList: ShoppingList) =
        shoppingListRepository.removeShoppingList(shoppingList)

    override suspend fun getShoppingItems(shoppingListId: Int): List<ShoppingItem> =
        shoppingListRepository.getShoppingItems(shoppingListId)

    override suspend fun addShoppingItem(shoppingItem: ShoppingItem) =
        shoppingListRepository.addShoppingItem(shoppingItem)

    override suspend fun editShoppingItem(shoppingItem: ShoppingItem) =
        shoppingListRepository.editShoppingItem(shoppingItem)

    override suspend fun removeShoppingItem(shoppingItem: ShoppingItem) =
        shoppingListRepository.removeShoppingItem(shoppingItem)
}