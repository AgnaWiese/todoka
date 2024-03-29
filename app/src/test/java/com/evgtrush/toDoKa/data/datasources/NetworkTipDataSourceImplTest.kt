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
package com.evgtrush.toDoKa.data.datasources

import com.evgtrush.toDoKa.data.datasources.network.NetworkTipDataSource
import com.evgtrush.toDoKa.data.datasources.network.NetworkTipDataSourceImpl
import com.evgtrush.toDoKa.data.models.network.TipDto
import com.evgtrush.toDoKa.data.network.TipService
import com.google.common.truth.Truth
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
class NetworkTipDataSourceImplTest {

    @Mock
    private lateinit var service: TipService

    private lateinit var dataSource: NetworkTipDataSource
    private lateinit var tipsDto: List<TipDto>

    @Before
    fun setUp() {
        dataSource = NetworkTipDataSourceImpl(service)
        tipsDto = listOf(
            TipDto(
                name = "Test Tip",
                type = "main dish",
                author = "ToDoKa"
            )
        )
    }

    @Test
    fun `getTips() - calls service and returns tips`() = runTest{
        whenever(service.getTips()).thenReturn(tipsDto)

        val result = dataSource.getTips()

        Mockito.verify(service, times(1)).getTips()
        Mockito.verifyNoMoreInteractions(service)

        Truth.assertThat(result).isEqualTo(tipsDto)
    }
}