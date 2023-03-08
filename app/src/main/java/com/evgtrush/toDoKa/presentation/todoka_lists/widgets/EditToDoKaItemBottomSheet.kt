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
package com.evgtrush.toDoKa.presentation.todoka_lists.widgets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import com.evgtrush.toDoKa.R
import com.evgtrush.toDoKa.databinding.BottomSheetEditTodokaListBinding
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.evgtrush.toDoKa.presentation.todoka_lists.details.ToDoKaListDetailsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditToDoKaItemBottomSheet(
    private val toDoKaItem: ToDoKaItem,
    private val viewModel: ToDoKaListDetailsViewModel
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "EditToDoKaListBottomSheet"
    }

    private var _binding: BottomSheetEditTodokaListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetEditTodokaListBinding.inflate(inflater, container, false)
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
                viewModel.removeToDoKaItem(toDoKaItem)
                dismiss()
            }
        }
    }

    private fun showRenameDialog() {
        val view = layoutInflater.inflate(R.layout.view_edit_text, null, false)
        val editText = view.findViewById<EditText>(R.id.textField).apply {
            setText(toDoKaItem.name)
            setSelection(length())
        }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_rename_item)
            .setView(view)
            .setPositiveButton(R.string.item_rename) { dialog, _ ->
                viewModel.editToDoKaItem(toDoKaItem.copy(
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