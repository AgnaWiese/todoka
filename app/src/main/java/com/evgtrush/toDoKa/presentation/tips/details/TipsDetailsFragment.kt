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
package com.evgtrush.toDoKa.presentation.tips.details

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.evgtrush.toDoKa.R
import com.evgtrush.toDoKa.databinding.FragmentTodokaTipsDetailsBinding
import com.evgtrush.toDoKa.domain.models.Tip
import com.evgtrush.toDoKa.domain.models.TipToDo
import com.evgtrush.toDoKa.presentation.tips.adapter.TipStepsAdapter
import com.evgtrush.toDoKa.presentation.utils.hideBottomNav
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TipsDetailsFragment : Fragment() {

    private val args: TipsDetailsFragmentArgs by navArgs()
    private val viewModel: TipsDetailsViewModel by viewModels()

    private var _binding: FragmentTodokaTipsDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodokaTipsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideBottomNav()

        val tip = args.tip
        with(binding) {
            toolbar.apply {
                setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                setNavigationOnClickListener { findNavController().navigateUp() }
                title = tip?.name ?: ""
            }
            tip?.let {
                btnShare.setOnClickListener { shareTip(tip) }
                btnFavorite.setOnCheckedChangeListener { checkBox, isChecked ->
                    if (isChecked) {
                        viewModel.createFavoriteTip(tip.name, tip.todo)
                    } else {
                        viewModel.deleteFavoriteTip(tip.name, tip.todo)
                    }
                }

                Glide //библиотека для загрузки картинок
                    .with(root.context)
                    .load(tip.imageUrl)
                    .into(image)

                rating.rating = it.rating.toFloat()
                description.text = tip.description

                if (tip.todo.isEmpty()) {
                    toDoContainer.visibility = View.GONE
                } else {
                    toDoContainer.visibility = View.VISIBLE
                    toDo.text = prepareToDoList(it.todo)
                    btnAddToToDoKaList.setOnClickListener { addToToDoKaList(tip) }
                }

                if (tip.steps.isEmpty()) {
                    toDoStepsContainer.visibility = View.GONE
                } else {
                    toDoStepsList.adapter = TipStepsAdapter(tip.steps)
                    toDoStepsContainer.visibility = View.VISIBLE
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    Log.d("TipsDetailsFragment", "UI update: $it")

                    when {
                        it.showCreateToDoKaListMessageOK -> {
                            showSnackBar(R.string.added_to_toDoKa_list)
                            binding.btnAddToToDoKaList.isEnabled = false
                        }
                        it.showAddToDoKaFavoriteTipMessageOK -> {
                            showSnackBar(R.string.added_to_favorite)
                        }
                        it.showDeleteToDoKaFavoriteTipMessageOK -> {
                            showSnackBar(R.string.deleted_from_favorite)
                        }
                        it.isError -> showSnackBar(R.string.general_error)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSnackBar(@StringRes message: Int) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        viewModel.userMessageShown()
    }

    private fun shareTip(tip: Tip) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, tipToString(tip))
        }

        startActivity(Intent.createChooser(intent, getString(R.string.share)))
    }

    private fun addToToDoKaList(tip: Tip) {
        if (tip.todo.isNotEmpty()) {
            viewModel.createToDoKaListByToDo(tip.name, tip.todo)
        }
    }

    private fun tipToString(tip: Tip): String =
        """
                Tip: ${tip.name},
                Description: ${tip.description} ,
                toDo: ${prepareToDoList(tip.todo)} 
        """.trimIndent()

    private fun prepareToDoList(toDo: List<TipToDo>): String =
        toDo.joinToString(
            separator = "\n",
            transform = {
                if (it.qty.isEmpty()) {
                    "– ${it.name}"
                } else {
                    "– ${it.name}: ${it.qty}"
                }
            }
        )
}