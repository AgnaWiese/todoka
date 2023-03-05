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

import com.evgtrush.foodbag.data.datasources.network.NetworkRecipeDataSource
import com.evgtrush.foodbag.data.datasources.network.NetworkRecipeDataSourceImpl
import com.evgtrush.foodbag.data.mappers.RecipeMapper
import com.evgtrush.foodbag.data.mappers.RecipeMapperImpl
import com.evgtrush.foodbag.data.network.RecipeRetrofitConstants
import com.evgtrush.foodbag.data.network.RecipeService
import com.evgtrush.foodbag.data.repositories.RecipeRepositoryImpl
import com.evgtrush.foodbag.domain.interactors.RecipeInteractor
import com.evgtrush.foodbag.domain.interactors.RecipeInteractorImpl
import com.evgtrush.foodbag.domain.repositories.RecipeRepository
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