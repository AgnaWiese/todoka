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
package com.evgtrush.toDoKa.domain.interactors

import com.evgtrush.toDoKa.domain.models.Tip
import com.evgtrush.toDoKa.domain.repositories.TipRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TipInteractorImplTest {

    @Mock
    private lateinit var repository: TipRepository

    private lateinit var interactor: TipInteractor
    private lateinit var tips: List<Tip>

    @Before
    fun setUp() {
        interactor = TipInteractorImpl(repository)
        tips = listOf(
            Tip(
                name = "Test Tip",
                type = "main dish",
                author = "ToDoKa"
            )
        )
    }

    @Test
    fun `getTips() - calls repository and returns tips`() = runTest {
        whenever(repository.getTips()).thenReturn(tips)

        val result = interactor.getTip()

        verify(repository, times(1)).getTips()
        verifyNoMoreInteractions(repository)

        assertThat(result).isEqualTo(tips)
    }
}