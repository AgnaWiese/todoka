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
package com.evgtrush.foodbag.data.mappers

import com.evgtrush.foodbag.data.models.db.ShoppingItemEntity
import com.evgtrush.foodbag.domain.models.ShoppingItem
import javax.inject.Inject

class ShoppingItemMapperImpl @Inject constructor(): ShoppingItemMapper {

    override fun convert(entity: ShoppingItemEntity): ShoppingItem =
        ShoppingItem(
            id = entity.uid,
            name = entity.name,
            bought = entity.bought,
            shoppingListId = entity.shoppingListId
        )

    override fun reverse(model: ShoppingItem): ShoppingItemEntity =
        ShoppingItemEntity(
            uid = model.id,
            name = model.name,
            bought = model.bought,
            shoppingListId = model.shoppingListId
        )
}