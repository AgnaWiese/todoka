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
package com.evgtrush.foodbag.domain.interactors

import com.evgtrush.foodbag.domain.models.ShoppingItem
import com.evgtrush.foodbag.domain.models.ShoppingList
import com.evgtrush.foodbag.domain.repositories.ShoppingListRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ShoppingListInteractorImplTest {

    @Mock
    private lateinit var repository: ShoppingListRepository

    private lateinit var interactor: ShoppingListInteractor
    private lateinit var shoppingLists: List<ShoppingList>
    private lateinit var shoppingItems: List<ShoppingItem>

    @Before
    fun setUp() {
        interactor = ShoppingListInteractorImpl(repository)
        shoppingLists = listOf(
            ShoppingList(
                id = 1,
                name = "Test List 1",
                progress = 0
            ),
            ShoppingList(
                id = 2,
                name = "Test List 2",
                progress = 50
            ),
            ShoppingList(
                id = 3,
                name = "Test List 3",
                progress = 100
            )
        )
        shoppingItems = listOf(
            ShoppingItem(
                id = 1,
                name = "Cucumber"
            ),
            ShoppingItem(
                id = 2,
                name = "Orange"
            )
        )
    }

    @Test
    fun `getShoppingLists() - calls repository and returns shopping lists`() = runTest {
        whenever(repository.getShoppingLists()).thenReturn(shoppingLists)

        val result = interactor.getShoppingLists()

        verify(repository, times(1)).getShoppingLists()
        verifyNoMoreInteractions(repository)

        assertThat(result).isEqualTo(shoppingLists)
    }

    @Test
    fun `getShoppingItems() - calls repository and returns shopping items`() = runTest {
        val id = 1

        whenever(repository.getShoppingItems(id)).thenReturn(shoppingItems)

        val result = interactor.getShoppingItems(id)

        verify(repository, times(1)).getShoppingItems(id)
        verifyNoMoreInteractions(repository)

        assertThat(result).isEqualTo(shoppingItems)
    }
}