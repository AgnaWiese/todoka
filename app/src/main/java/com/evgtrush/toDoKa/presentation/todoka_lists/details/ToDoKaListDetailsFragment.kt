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
package com.evgtrush.toDoKa.presentation.todoka_lists.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.evgtrush.toDoKa.R
import com.evgtrush.toDoKa.databinding.FragmentTodokaListDetailsBinding
import com.evgtrush.toDoKa.domain.models.ToDoKaItem
import com.evgtrush.toDoKa.presentation.todoka_lists.adapter.ToDoKaListItemsAdapter
import com.evgtrush.toDoKa.presentation.utils.hideBottomNav
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoKaListDetailsFragment : Fragment(R.layout.fragment_todoka_list_details)  {

    private val viewModel: ToDoKaListDetailsViewModel by viewModels()

    private val args: ToDoKaListDetailsFragmentArgs by navArgs()

    private var _binding: FragmentTodokaListDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodokaListDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBottomNav()

       val tip = args.todokaList
        with(binding) {
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener { findNavController().navigateUp() }
                title = tip?.name
            }
        }

        binding.fab.setOnClickListener {
            showAddItemDialog()
        }

        getToDoKaItemsAsync()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getToDoKaItemsAsync() {
        args.todokaList?.let { it ->
            binding.progress.visibility = View.VISIBLE
            viewModel.getToDoKaListItem(it.id)
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.uiState.collectLatest {
                        Log.d("ToDoKaListDetailsFragment", "UI update: $it")

                        binding.toDoKaItems.adapter = ToDoKaListItemsAdapter(
                            it.toDoKaItems, parentFragmentManager, viewModel
                        )

                        when {
                            it.isError -> showSnackBar(R.string.general_error)
                        }

                        binding.progress.visibility = View.GONE

                        binding.hint.visibility = if (it.toDoKaItems.isEmpty()) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.userMessageShown()
    }

    private fun showAddItemDialog() {
        val view = layoutInflater.inflate(R.layout.view_edit_text, null, false)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_add_item)
            .setView(view)
            .setPositiveButton(R.string.item_create) { dialog, _ ->
                viewModel.addToDoKaItem(
                    ToDoKaItem(
                        name = view.findViewById<EditText>(R.id.textField).text.toString(),
                        toDoKaListId = args.todokaList?.id ?: 0 //TODO: dirty
                    )
                )
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}