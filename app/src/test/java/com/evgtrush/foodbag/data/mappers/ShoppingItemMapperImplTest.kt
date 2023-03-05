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
package com.evgtrush.foodbag.data.mappers

import com.evgtrush.foodbag.data.models.db.ShoppingItemEntity
import com.evgtrush.foodbag.domain.models.ShoppingItem
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ShoppingItemMapperImplTest {

    private lateinit var mapper: ShoppingItemMapper
    private lateinit var shoppingItemEntity: ShoppingItemEntity
    private lateinit var shoppingItem: ShoppingItem

    @Before
    fun setUp() {
        mapper = ShoppingItemMapperImpl()
        shoppingItemEntity =
            ShoppingItemEntity(
                uid = 1,
                shoppingListId = 2,
                name = "Огурец",
                bought = true
            )
        shoppingItem =
            ShoppingItem(
                id = 1,
                shoppingListId = 2,
                name = "Огурец",
                bought = true
            )
    }

    @Test
    fun `convert() - data model is converted correctly to domain model`() {
        runBlocking {
            val result = mapper.convert(shoppingItemEntity)

            Truth.assertThat(result).isEqualTo(shoppingItem)
        }
    }
}