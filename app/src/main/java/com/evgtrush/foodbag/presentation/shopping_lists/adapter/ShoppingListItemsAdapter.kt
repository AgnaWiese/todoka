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
package com.evgtrush.foodbag.presentation.shopping_lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.evgtrush.foodbag.databinding.ListItemShoppingItemBinding
import com.evgtrush.foodbag.domain.models.ShoppingItem
import com.evgtrush.foodbag.presentation.shopping_lists.details.ShoppingListDetailsViewModel
import com.evgtrush.foodbag.presentation.shopping_lists.widgets.EditShoppingItemBottomSheet

internal class ShoppingListItemsAdapter(
    private val shoppingListItems: List<ShoppingItem>,
    private val fragmentManager: FragmentManager,
    private val viewModel: ShoppingListDetailsViewModel
) : RecyclerView.Adapter<ShoppingListItemsAdapter.ShoppingListItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListItemsViewHolder =
        ShoppingListItemsViewHolder(
            ListItemShoppingItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ShoppingListItemsViewHolder, position: Int) =
        holder.bindView(shoppingListItems[position], fragmentManager, viewModel)

    override fun getItemCount(): Int = shoppingListItems.size

    internal class ShoppingListItemsViewHolder(private val binding: ListItemShoppingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(shoppingItem: ShoppingItem, fragmentManager: FragmentManager, viewModel: ShoppingListDetailsViewModel) {
            with(binding) {
                title.text = shoppingItem.name
                title.isChecked = shoppingItem.bought

                title.setOnClickListener {
                    viewModel.editShoppingItem(shoppingItem.copy(
                        bought = title.isChecked
                    ))
                }

                more.setOnClickListener {
                    val modalBottomSheet = EditShoppingItemBottomSheet(shoppingItem, viewModel)
                    modalBottomSheet.show(fragmentManager, EditShoppingItemBottomSheet.TAG)
                }
            }
        }
    }
}