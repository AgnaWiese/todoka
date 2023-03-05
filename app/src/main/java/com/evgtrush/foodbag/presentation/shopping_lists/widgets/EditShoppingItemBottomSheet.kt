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
package com.evgtrush.foodbag.presentation.shopping_lists.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import com.evgtrush.foodbag.R
import com.evgtrush.foodbag.databinding.BottomSheetEditShoppingListBinding
import com.evgtrush.foodbag.domain.models.ShoppingItem
import com.evgtrush.foodbag.presentation.shopping_lists.details.ShoppingListDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditShoppingItemBottomSheet(
    private val shoppingItem: ShoppingItem,
    private val viewModel: ShoppingListDetailsViewModel
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "EditShoppingListBottomSheet"
    }

    private var _binding: BottomSheetEditShoppingListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetEditShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            title.text = getString(R.string.edit_item)
            itemRename.setOnClickListener {
                showRenameDialog()
                dismiss()
            }
            itemDelete.setOnClickListener {
                viewModel.removeShoppingItem(shoppingItem)
                dismiss()
            }
        }
    }

    private fun showRenameDialog() {
        val view = layoutInflater.inflate(R.layout.view_edit_text, null, false)
        val editText = view.findViewById<EditText>(R.id.textField).apply {
            setText(shoppingItem.name)
            setSelection(length())
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_rename_item)
            .setView(view)
            .setPositiveButton(R.string.item_rename) { dialog, _ ->
                viewModel.editShoppingItem(shoppingItem.copy(
                    name = editText.text.toString()
                ))
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}