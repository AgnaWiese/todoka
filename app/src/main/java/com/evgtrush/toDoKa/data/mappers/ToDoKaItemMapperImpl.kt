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
package com.evgtrush.toDoKa.data.mappers

import com.evgtrush.toDoKa.data.models.db.ToDoKaItemEntity
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import javax.inject.Inject

class ToDoKaItemMapperImpl @Inject constructor(): ToDoKaItemMapper {

    override fun convert(entity: ToDoKaItemEntity): ToDoKaItem =
        ToDoKaItem(
            id = entity.uid,
            name = entity.name,
            bought = entity.bought,
            toDoKaListId = entity.toDoKaListId
        )

    override fun reverse(model: ToDoKaItem): ToDoKaItemEntity =
        ToDoKaItemEntity(
            uid = model.id,
            name = model.name,
            bought = model.bought,
            toDoKaListId = model.toDoKaListId
        )
}