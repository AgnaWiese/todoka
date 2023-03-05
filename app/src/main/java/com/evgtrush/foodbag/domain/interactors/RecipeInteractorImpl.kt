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

import com.evgtrush.foodbag.domain.models.Recipe
import com.evgtrush.foodbag.domain.repositories.RecipeRepository
import javax.inject.Inject

class RecipeInteractorImpl @Inject constructor(
    private val repository: RecipeRepository
): RecipeInteractor {

    override suspend fun getRecipes(): List<Recipe> = repository.getRecipes()
}