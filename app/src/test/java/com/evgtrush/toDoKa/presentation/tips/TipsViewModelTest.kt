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
package com.evgtrush.toDoKa.presentation.tips

import com.evgtrush.toDoKa.domain.interactors.TipInteractor
import com.evgtrush.toDoKa.domain.models.Tip
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
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
class TipsViewModelTest {

    @Mock
    private lateinit var interactor: TipInteractor

    private lateinit var viewModel: TipsViewModel
    private lateinit var tips: List<Tip>
    private lateinit var uiState: TipsViewModel.TipsUiState

    @Before
    fun setUp() {
        viewModel = TipsViewModel(interactor)
        tips = listOf(
            Tip(
                name = "Test Tip",
                type = "main dish",
                author = "toDoKa"
            )
        )
    }

    @Test
    fun `getTips() - calls interactor and returns tips`() = runTest {
        whenever(interactor.getTip()).thenReturn(tips)

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try {
            viewModel.getTips()

            Mockito.verify(interactor, times(1)).getTip()
            Mockito.verifyNoMoreInteractions(interactor)

            assertThat(viewModel.uiState.value).isEqualTo(
                TipsViewModel.TipsUiState(
                    isError = false,
                    tips = tips
                )
            )
        } finally {
            Dispatchers.resetMain()
        }
    }

    @Test
    fun `getTips() - calls interactor and returns error state`() = runTest {
        whenever(interactor.getTip()).thenThrow(RuntimeException())

        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(testDispatcher)
        try {
            viewModel.getTips()

            Mockito.verify(interactor, times(1)).getTip()
            Mockito.verifyNoMoreInteractions(interactor)

            assertThat(viewModel.uiState.value).isEqualTo(
                TipsViewModel.TipsUiState(
                    isError = true
                )
            )
        } finally {
            Dispatchers.resetMain()
        }
    }
}