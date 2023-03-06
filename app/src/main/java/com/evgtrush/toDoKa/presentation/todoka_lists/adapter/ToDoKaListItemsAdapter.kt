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
package com.evgtrush.toDoKa.presentation.todoka_lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.evgtrush.toDoKa.presentation.todoka_lists.details.ToDoKaListDetailsViewModel

internal class ToDoKaListItemsAdapter(
    private val toDoKaListItems: List<ToDoKaItem>,
    private val fragmentManager: FragmentManager,
    private val viewModel: ToDoKaListDetailsViewModel
) : RecyclerView.Adapter<ToDoKaListItemsAdapter.ToDoKaListItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoKaListItemsViewHolder =
        ToDoKaListItemsViewHolder(
            ListItemToDoKaItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ToDoKaListItemsViewHolder, position: Int) =
        holder.bindView(toDoKaListItems[position], fragmentManager, viewModel)

    override fun getItemCount(): Int = toDoKaListItems.size

    internal class ToDoKaListItemsViewHolder(private val binding: ListItemToDoKaItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(toDoKaItem: ToDoKaItem, fragmentManager: FragmentManager, viewModel: ToDoKaListDetailsViewModel) {
            with(binding) {
                title.text = toDoKaItem.name
                title.isChecked = toDoKaItem.bought

                title.setOnClickListener {
                    viewModel.editToDoKaItem(toDoKaItem.copy(
                        bought = title.isChecked
                    ))
                }

                more.setOnClickListener {
                    val modalBottomSheet = EditToDoKaItemBottomSheet(toDoKaItem, viewModel)
                    modalBottomSheet.show(fragmentManager, EditToDoKaItemBottomSheet.TAG)
                }
            }
        }
    }
}