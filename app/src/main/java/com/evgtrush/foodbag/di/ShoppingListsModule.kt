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
package com.evgtrush.foodbag.di

import com.evgtrush.foodbag.data.datasources.db.AppDatabase
import com.evgtrush.foodbag.data.datasources.db.ShoppingItemDao
import com.evgtrush.foodbag.data.datasources.db.ShoppingListDao
import com.evgtrush.foodbag.data.mappers.ShoppingItemMapper
import com.evgtrush.foodbag.data.mappers.ShoppingItemMapperImpl
import com.evgtrush.foodbag.data.mappers.ShoppingListMapper
import com.evgtrush.foodbag.data.mappers.ShoppingListMapperImpl
import com.evgtrush.foodbag.data.repositories.MockShoppingListRepositoryImpl
import com.evgtrush.foodbag.data.repositories.ShoppingListRepositoryImpl
import com.evgtrush.foodbag.domain.interactors.ShoppingListInteractor
import com.evgtrush.foodbag.domain.interactors.ShoppingListInteractorImpl
import com.evgtrush.foodbag.domain.repositories.ShoppingListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ShoppingListsModule {

    companion object {
        @Provides
        @ViewModelScoped
        fun provideShoppingListDao(appDatabase: AppDatabase): ShoppingListDao = appDatabase.shoppingList()

        @Provides
        @ViewModelScoped
        fun provideShoppingItemDao(appDatabase: AppDatabase): ShoppingItemDao = appDatabase.shoppingItem()
    }

    @Binds
    @ViewModelScoped
    abstract fun bindShoppingItemMapper(impl: ShoppingItemMapperImpl): ShoppingItemMapper

    @Binds
    @ViewModelScoped
    abstract fun bindShoppingListMapper(impl: ShoppingListMapperImpl): ShoppingListMapper

    @Binds
    @ViewModelScoped
    abstract fun bindShoppingListRepository(impl: MockShoppingListRepositoryImpl): ShoppingListRepository

//    @Binds
//    @ViewModelScoped
//    abstract fun bindShoppingListRepository(impl: MockShoppingListRepositoryImpl): ShoppingListRepository

    @Binds
    @ViewModelScoped
    abstract fun bindShoppingListInteractor(impl: ShoppingListInteractorImpl): ShoppingListInteractor
}