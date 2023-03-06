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
package com.evgtrush.toDoKa.data.datasources.db

import androidx.room.*
import com.evgtrush.toDoKa.data.datasources.db.AppDatabase.Companion.TABLE_SHOPPING_LISTS
import com.evgtrush.toDoKa.data.models.db.TоDoKaListEntity

@Dao
interface ToDoKaListDao {

    @Query("SELECT * FROM $TABLE_SHOPPING_LISTS")
    suspend fun getAll(): List<TоDoKaListEntity>

    @Insert
    suspend fun insertAll(vararg shoppingLists: TоDoKaListEntity): LongArray

    @Update
    suspend fun update(shoppingList: TоDoKaListEntity)

    @Delete
    suspend fun delete(shoppingList: TоDoKaListEntity)
}