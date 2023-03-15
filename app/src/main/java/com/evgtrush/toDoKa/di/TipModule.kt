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

import com.evgtrush.toDoKa.data.datasources.network.NetworkTipDataSource
import com.evgtrush.toDoKa.data.datasources.network.NetworkTipDataSourceImpl
import com.evgtrush.toDoKa.data.mappers.TipMapper
import com.evgtrush.toDoKa.data.mappers.TipMapperImpl
import com.evgtrush.toDoKa.data.network.TipRetrofitConstants
import com.evgtrush.toDoKa.data.network.TipService
import com.evgtrush.toDoKa.data.repositories.TipRepositoryImpl
import com.evgtrush.toDoKa.domain.interactors.TipInteractor
import com.evgtrush.toDoKa.domain.interactors.TipInteractorImpl
import com.evgtrush.toDoKa.domain.repositories.TipRepository
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
abstract class TipModule {

    companion object {
        @Provides
        @ViewModelScoped
        fun provideRetrofitTipService(gson: Gson): TipService = Retrofit.Builder()
            .baseUrl(TipRetrofitConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TipService::class.java)
    }

    @Binds
    @ViewModelScoped
    abstract fun bindNetworkTipDataSource(impl: NetworkTipDataSourceImpl): NetworkTipDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindTipMapper(impl: TipMapperImpl): TipMapper

    @Binds
    @ViewModelScoped
    abstract fun bindTipRepository(impl: TipRepositoryImpl): TipRepository

    @Binds
    @ViewModelScoped
    abstract fun bindTipInteractor(impl: TipInteractorImpl): TipInteractor
}