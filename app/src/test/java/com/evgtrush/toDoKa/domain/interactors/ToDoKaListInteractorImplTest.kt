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

import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.evgtrush.toDoKa.domain.models.ToDoKaList
import com.evgtrush.toDoKa.domain.repositories.ToDoKaListRepository
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
class ToDoKaListInteractorImplTest {

    @Mock
    private lateinit var repository: ToDoKaListRepository

    private lateinit var interactor: ToDoKaListInteractor
    private lateinit var toDoKaLists: List<ToDoKaList>
    private lateinit var toDoKaItems: List<ToDoKaItem>

    @Before
    fun setUp() {
        interactor = ToDoKaListInteractorImpl(repository)
        toDoKaLists = listOf(
            ToDoKaList(
                id = 1,
                name = "Test List 1",
                progress = 0
            ),
            ToDoKaList(
                id = 2,
                name = "Test List 2",
                progress = 50
            ),
            ToDoKaList(
                id = 3,
                name = "Test List 3",
                progress = 100
            )
        )
        toDoKaItems = listOf(
            ToDoKaItem(
                id = 1,
                name = "Cucumber"
            ),
            ToDoKaItem(
                id = 2,
                name = "Orange"
            )
        )
    }

    @Test
    fun `getToDoKaLists() - calls repository and returns ToDoKa lists`() = runTest {
        whenever(repository.getToDoKaLists()).thenReturn(toDoKaLists)

        val result = interactor.getToDoKaLists()

        verify(repository, times(1)).getToDoKaLists()
        verifyNoMoreInteractions(repository)

        assertThat(result).isEqualTo(toDoKaLists)
    }

    @Test
    fun `getToDoKaItems() - calls repository and returns ToDoKa items`() = runTest {
        val id = 1

        whenever(repository.getToDoKaItems(id)).thenReturn(toDoKaItems)

        val result = interactor.getToDoKaItems(id)

        verify(repository, times(1)).getToDoKaItems(id)
        verifyNoMoreInteractions(repository)

        assertThat(result).isEqualTo(toDoKaItems)
    }
}