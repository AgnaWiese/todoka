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
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.evgtrush.foodbag.databinding.ListItemShoppingListBinding
import com.evgtrush.foodbag.domain.models.ShoppingList
import com.evgtrush.foodbag.presentation.shopping_lists.ShoppingListsFragmentDirections
import com.evgtrush.foodbag.presentation.shopping_lists.ShoppingListsViewModel
import com.evgtrush.foodbag.presentation.shopping_lists.widgets.EditShoppingListBottomSheet

internal class ShoppingListsAdapter(
    private val shoppingLists: List<ShoppingList>,
    private val fragmentManager: FragmentManager,
    private val viewModel: ShoppingListsViewModel
) : RecyclerView.Adapter<ShoppingListsAdapter.ShoppingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder =
        ShoppingListViewHolder(
            ListItemShoppingListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) =
        holder.bindView(shoppingLists[position], fragmentManager, viewModel)

    override fun getItemCount(): Int = shoppingLists.size

    internal class ShoppingListViewHolder(private val binding: ListItemShoppingListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(shoppingList: ShoppingList, fragmentManager: FragmentManager, viewModel: ShoppingListsViewModel) {
            with(binding) {
                title.text = shoppingList.name
                progress.progress = shoppingList.progress
                ready.visibility = if (shoppingList.progress >= 100) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

                root.setOnClickListener {
                    val direction = ShoppingListsFragmentDirections.actionOpenShoppingList(shoppingList)
                    Navigation.findNavController(it).navigate(direction)
                }

                more.setOnClickListener {
                    val modalBottomSheet = EditShoppingListBottomSheet(shoppingList, viewModel)
                    modalBottomSheet.show(fragmentManager, EditShoppingListBottomSheet.TAG)
                }
            }
        }
    }
}