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

import com.evgtrush.toDoKa.data.models.network.*
import com.evgtrush.toDoKa.domain.models.Tip
import com.evgtrush.toDoKa.domain.models.TipToDo
import com.evgtrush.toDoKa.domain.models.TipStep
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TipMapperImplTest {

    private lateinit var mapper: TipMapper
    private lateinit var tipDto: TipDto
    private lateinit var tip: Tip

    @Before
    fun setUp() {
        mapper = TipMapperImpl()
        tipDto =
            TipDto(
                name = "Test tip",
                type = "main dish",
                previewImageUrl = "https://test_preview.png",
                imageUrl = "https://test.png",
                author = "ToDoKa",
                dateAdded = "01.01.2023",
                description = "Just open a fridge",
                complexity = 1,
                rating = 3,
                toDo = listOf(
                    TipToDoDto(
                        name = "Carrot",
                        qty = "1 kg."
                    )
                ),
                steps = listOf(
                    TipStepDto(
                        image = "https://test2.png",
                        text = "Make everything cool"
                    )
                )
            )
        tip =
            Tip(
                name = "Test tip",
                type = "main dish",
                previewImageUrl = "https://test_preview.png",
                imageUrl = "https://test.png",
                author = "ToDoKa",
                dateAdded = "01.01.2023",
                description = "Just open a fridge",
                complexity = 1,
                rating = 3,
                todo = listOf(
                    TipToDo(
                        name = "Carrot",
                        qty = "1 kg."
                    )
                ),
                steps = listOf(
                    TipStep(
                        imageUrl = "https://test2.png",
                        text = "Make everything cool"
                    )
                )
            )
    }

    @Test
    fun `convert() - data model is converted correctly to domain model`() {
        runBlocking {
            val result = mapper.convert(tipDto)

            Truth.assertThat(result).isEqualTo(tip)
        }
    }
}