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
package com.evgtrush.toDoKa.data.mappers

import com.evgtrush.toDoKa.data.models.db.ToDoKaItemEntity
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ToDoKaItemMapperImplTest {

    private lateinit var mapper: ToDoKaItemMapper
    private lateinit var toDoKaItemEntity: ToDoKaItemEntity
    private lateinit var toDoKaItem: ToDoKaItem

    @Before
    fun setUp() {
        mapper = ToDoKaItemMapperImpl()
        toDoKaItemEntity =
            ToDoKaItemEntity(
                uid = 1,
                toDoKaListId = 2,
                name = "Огурец",
                bought = true
            )
        toDoKaItem =
            ToDoKaItem(
                id = 1,
                toDoKaListId = 2,
                name = "Огурец",
                bought = true
            )
    }

    @Test
    fun `convert() - data model is converted correctly to domain model`() {
        runBlocking {
            val result = mapper.convert(toDoKaItemEntity)

            Truth.assertThat(result).isEqualTo(toDoKaItem)
        }
    }
}