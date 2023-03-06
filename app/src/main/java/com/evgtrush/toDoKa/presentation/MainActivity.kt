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
package com.evgtrush.toDoKa.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.evgtrush.toDoKa.R
import com.evgtrush.toDoKa.databinding.ActivityMainBinding
import com.evgtrush.toDoKa.presentation.utils.doOnApplyWindowInsets
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var navView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupInsets()

        navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView?.setupWithNavController(navController)
    }

    fun showBottomNav() {
        navView?.visibility = View.VISIBLE
    }

    fun hideBottomNav() {
        navView?.visibility = View.GONE
    }

    fun switchBottomNavigation(navigationId: Int) {
        navView?.selectedItemId = navigationId
    }

    private fun setupInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding.navView.doOnApplyWindowInsets { view, insets, padding ->
            view.updatePadding(
                bottom = padding.bottom + insets.systemWindowInsetBottom
            )
        }
    }
}