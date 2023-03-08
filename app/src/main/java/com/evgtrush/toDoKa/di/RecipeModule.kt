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

import com.evgtrush.toDoKa.data.datasources.network.NetworkRecipeDataSource
import com.evgtrush.toDoKa.data.datasources.network.NetworkRecipeDataSourceImpl
import com.evgtrush.toDoKa.data.mappers.RecipeMapper
import com.evgtrush.toDoKa.data.mappers.RecipeMapperImpl
import com.evgtrush.toDoKa.data.network.RecipeRetrofitConstants
import com.evgtrush.toDoKa.data.network.RecipeService
import com.evgtrush.toDoKa.data.repositories.RecipeRepositoryImpl
import com.evgtrush.toDoKa.domain.interactors.RecipeInteractor
import com.evgtrush.toDoKa.domain.interactors.RecipeInteractorImpl
import com.evgtrush.toDoKa.domain.repositories.RecipeRepository
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
abstract class RecipeModule {

    companion object {
        @Provides
        @ViewModelScoped
        fun provideRetrofitRecipeService(gson: Gson): RecipeService = Retrofit.Builder()
            .baseUrl(RecipeRetrofitConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RecipeService::class.java)
    }

    @Binds
    @ViewModelScoped
    abstract fun bindNetworkRecipeDataSource(impl: NetworkRecipeDataSourceImpl): NetworkRecipeDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindRecipeMapper(impl: RecipeMapperImpl): RecipeMapper

    @Binds
    @ViewModelScoped
    abstract fun bindRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository

    @Binds
    @ViewModelScoped
    abstract fun bindRecipeInteractor(impl: RecipeInteractorImpl): RecipeInteractor
}