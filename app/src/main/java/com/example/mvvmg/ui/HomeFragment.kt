package com.example.mvvmg.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmg.R
import com.example.mvvmg.adapters.UserAdapter
import com.example.mvvmg.database.AppDatabase
import com.example.mvvmg.databinding.FragmentHomeBinding
import com.example.mvvmg.repository.UserRepository
import com.example.mvvmg.retrofit.ApiClient
import com.example.mvvmg.utils.*
import com.example.mvvmg.viewmodels.UserViewModel
import com.example.mvvmg.viewmodels.ViewModelFactory
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var userViewModel: UserViewModel
    private val TAG = "HomeFragment"
    private lateinit var appDatabase: AppDatabase
    private lateinit var userAdapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = AppDatabase.getInstance(requireContext())
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                UserRepository(ApiClient.apiService, appDatabase.userDao()),
                NetworkHelper(requireContext())
            )
        )[UserViewModel::class.java]

        userAdapter = UserAdapter()
        binding.rv.adapter = userAdapter

        loadUi()

        binding.swipe.setOnRefreshListener {
            loadUi()
        }
    }

    fun loadUi() {
        lifecycleScope.launch {
            userViewModel.fetchUsers().collect {
                when (it) {
                    is UserResource.Loading -> {
                        binding.swipe.isRefreshing = true
                    }
                    is UserResource.Error -> {
                        binding.swipe.isRefreshing = false
                        binding.tv.show()
                        requireContext().makeToast(it.message)
                    }
                    is UserResource.Success -> {
                        binding.swipe.isRefreshing = false
                        binding.rv.show()
                        binding.tv.hide()
                        userAdapter.submitList(it.list)
                    }
                }
            }
        }
    }
}