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
package com.evgtrush.toDoKa.data.repositories

import com.evgtrush.toDoKa.data.datasources.network.NetworkTipDataSource
import com.evgtrush.toDoKa.data.mappers.TipMapperImpl
import com.evgtrush.toDoKa.data.models.network.TipDto
import com.evgtrush.toDoKa.domain.models.Tip
import com.evgtrush.toDoKa.domain.repositories.TipRepository
import com.evgtrush.toDoKa.presentation.tips.details.TipsDetailsFragment
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TipRepositoryImplTest {

    @Mock
    private lateinit var dataSource: NetworkTipDataSource

    private lateinit var tipsDto: List<TipDto>
    private lateinit var tips: List<Tip>
    private lateinit var repository: TipRepository

    @Before
    fun setUp() {
        repository = TipRepositoryImpl(
            dataSource,
            TipMapperImpl(),
            Dispatchers.Default
        )
        tipsDto = listOf(
            TipDto(
                name = "Test Tip",
                type = "main dish",
                author = "ToDoKa"
            )
        )
        tips = listOf(
            Tip(
                name = "Test Tips",
                type = "main dish",
                author = "ToDoKa"
            )
        )
    }

    @Test
    fun `getTips() - calls datasource and returns tips`() = runTest {
        whenever(dataSource.getTips()).thenReturn(tipsDto)

        val result = repository.getTips()

        Mockito.verify(dataSource, times(1)).getTips()
        Mockito.verifyNoMoreInteractions(dataSource)

        assertThat(result).isEqualTo(tips)
    }
}