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

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evgtrush.foodbag.data.models.db.ShoppingItemEntity
import com.evgtrush.foodbag.data.models.db.ShoppingListEntity

@Database(entities = [
    ShoppingListEntity::class,
    ShoppingItemEntity::class
 ], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "foodbag_db"
        const val TABLE_SHOPPING_ITEMS = "shopping_items"
        const val TABLE_SHOPPING_LISTS = "shopping_lists"
    }

    abstract fun shoppingList(): ShoppingListDao
    abstract fun shoppingItem(): ShoppingItemDao
}