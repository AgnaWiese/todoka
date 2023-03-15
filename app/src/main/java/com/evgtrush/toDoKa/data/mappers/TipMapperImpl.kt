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

import com.evgtrush.toDoKa.data.models.network.TipDto
import com.evgtrush.toDoKa.domain.models.Tip
import com.evgtrush.toDoKa.domain.models.TipToDo
import com.evgtrush.toDoKa.domain.models.TipStep
import javax.inject.Inject

class TipMapperImpl @Inject constructor(): TipMapper {

    override fun convert(dto: TipDto): Tip =
        Tip(
            name = dto.name,
            type = dto.type,
            previewImageUrl = dto.previewImageUrl,
            imageUrl = dto.imageUrl,
            author = dto.author,
            dateAdded = dto.dateAdded,
            description = dto.description,
            complexity = dto.complexity,
            rating = dto.rating,
            todo = dto.toDo.map {
                TipToDo(
                    name = it.name,
                    qty = it.qty
                )
            },
            steps = dto.steps.map {
                TipStep(
                    imageUrl = it.image,
                    text = it.text
                )
            }
        )
}