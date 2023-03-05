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
package com.evgtrush.foodbag.data.datasources.db

import androidx.room.*
import com.evgtrush.foodbag.data.datasources.db.AppDatabase.Companion.TABLE_SHOPPING_ITEMS
import com.evgtrush.foodbag.data.models.db.ShoppingItemEntity

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM $TABLE_SHOPPING_ITEMS WHERE shopping_list_id = (:id)")
    suspend fun getAllByShoppingListId(id: Int): List<ShoppingItemEntity>
    
    @Query("SELECT COUNT(uid) FROM $TABLE_SHOPPING_ITEMS WHERE shopping_list_id = (:id) AND bought = true")
    suspend fun getBoughtItemsCountByShoppingListId(id: Int): Int

    @Query("SELECT COUNT(uid) FROM $TABLE_SHOPPING_ITEMS WHERE shopping_list_id = (:id)")
    suspend fun getItemsCountByShoppingListId(id: Int): Int

    @Insert
    suspend fun insertAll(vararg shoppingItems: ShoppingItemEntity)

    @Update
    suspend fun update(shoppingItem: ShoppingItemEntity)

    @Delete
    suspend fun delete(shoppingItem: ShoppingItemEntity)
}