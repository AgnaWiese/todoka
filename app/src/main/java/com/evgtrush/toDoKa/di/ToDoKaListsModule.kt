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
package com.evgtrush.toDoKa.di

import com.evgtrush.toDoKa.data.datasources.db.AppDatabase
import com.evgtrush.toDoKa.data.datasources.db.ToDoKaItemDao
import com.evgtrush.toDoKa.data.datasources.db.ToDoKaListDao
import com.evgtrush.toDoKa.data.mappers.ToDoKaItemMapper
import com.evgtrush.toDoKa.data.mappers.ToDoKaItemMapperImpl
import com.evgtrush.toDoKa.data.mappers.ToDoKaListMapper
import com.evgtrush.toDoKa.data.mappers.ToDoKaListMapperImpl
import com.evgtrush.toDoKa.data.repositories.MockToDoKaListRepositoryImpl
import com.evgtrush.toDoKa.data.repositories.ToDoKaListRepositoryImpl
import com.evgtrush.toDoKa.domain.interactors.ToDoKaListInteractor
import com.evgtrush.toDoKa.domain.interactors.ToDoKaListInteractorImpl
import com.evgtrush.toDoKa.domain.repositories.ToDoKaListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ToDoKaListsModule {

    companion object {
        @Provides
        @ViewModelScoped
        fun provideToDoKaListDao(appDatabase: AppDatabase): ToDoKaListDao = appDatabase.toDoKaList()

        @Provides
        @ViewModelScoped
        fun provideToDoKaItemDao(appDatabase: AppDatabase): ToDoKaItemDao = appDatabase.toDoKaItem()
    }

    @Binds
    @ViewModelScoped
    abstract fun bindToDoKaItemMapper(impl: ToDoKaItemMapperImpl): ToDoKaItemMapper

    @Binds
    @ViewModelScoped
    abstract fun bindToDoKaListMapper(impl: ToDoKaListMapperImpl): ToDoKaListMapper

    @Binds
    @ViewModelScoped
    abstract fun bindToDoKaListRepository(impl: ToDoKaListRepositoryImpl): ToDoKaListRepository

//    @Binds
//    @ViewModelScoped
//    abstract fun bindToDoKaListRepository(impl: MockToDoKaListRepositoryImpl): ToDoKaListRepository

    @Binds
    @ViewModelScoped
    abstract fun bindToDoKaListInteractor(impl: ToDoKaListInteractorImpl): ToDoKaListInteractor
}