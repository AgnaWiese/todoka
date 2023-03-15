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
package com.evgtrush.toDoKa.presentation.tips.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.evgtrush.toDoKa.databinding.ListItemTipBinding
import com.evgtrush.toDoKa.domain.models.Tip
import com.evgtrush.toDoKa.presentation.tips.TipsFragmentDirections

internal class TipsAdapter(private val tips: List<Tip>) :
    RecyclerView.Adapter<TipsAdapter.TipViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder =
        TipViewHolder(
            ListItemTipBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) =
        holder.bindView(tips[position])

    override fun getItemCount(): Int = tips.size

    internal class TipViewHolder(private val binding: ListItemTipBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(tip: Tip) {
            with(binding) {
                headline.text = tip.name
                rating.rating = tip.rating.toFloat()
                supportingText.text = tip.description
                Glide
                    .with(root.context)
                    .load(tip.imageUrl)
                    .into(tipImage)
                materialCardContainer.setOnClickListener {
                    val direction = TipsFragmentDirections.actionOpenTip(tip)
                    Navigation.findNavController(it).navigate(direction)
                }
            }
        }
    }
}