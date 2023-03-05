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
package com.evgtrush.foodbag.presentation.shopping_lists

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
import com.evgtrush.foodbag.R
import com.evgtrush.foodbag.databinding.FragmentShoppingListsBinding
import com.evgtrush.foodbag.domain.models.ShoppingList
import com.evgtrush.foodbag.presentation.shopping_lists.adapter.ShoppingListsAdapter
import com.evgtrush.foodbag.presentation.utils.showBottomNav
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShoppingListsFragment : Fragment() {

    private val viewModel: ShoppingListsViewModel by viewModels()

    private var _binding: FragmentShoppingListsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showBottomNav()

        getShoppingListsAsync()

        binding.fab.setOnClickListener {
            showAddListDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getShoppingListsAsync() {
        binding.progress.visibility = View.VISIBLE
        viewModel.getShoppingLists()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    Log.d("ShoppingListsFragment", "UI update: $it")

                    binding.shoppingLists.adapter = ShoppingListsAdapter(
                        it.shoppingLists,
                        parentFragmentManager,
                        viewModel
                    )

                    when {
                        it.isError -> showSnackBar(R.string.general_error)
                    }

                    binding.progress.visibility = View.GONE

                    binding.hint.visibility = if (it.shoppingLists.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.userMessageShown()
    }

    private fun showAddListDialog() {
        val view = layoutInflater.inflate(R.layout.view_edit_text, null, false)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.title_create_list)
            .setView(view)
            .setPositiveButton(R.string.item_create) { dialog, _ ->
                viewModel.createShoppingList(
                    ShoppingList(
                        name = view.findViewById<EditText>(R.id.textField).text.toString()
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