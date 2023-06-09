package com.example.userandalbum.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userandalbum.R
import com.example.userandalbum.adapters.UserAdapter
import com.example.userandalbum.databinding.FragmentUserListBinding
import com.example.userandalbum.util.DataState
import com.example.userandalbum.util.logE
import com.example.userandalbum.util.notifyUser
import com.example.userandalbum.viewModels.UserDetailsViewModel

class UserListFragment : Fragment() {

    private lateinit var userDetailsViewModel: UserDetailsViewModel
    lateinit var userAdapter: UserAdapter
    private lateinit var binding: FragmentUserListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailsViewModel = ViewModelProvider(this)[UserDetailsViewModel::class.java]

        binding.swipeToRefresh.setOnRefreshListener {
            getUserData()
            binding.swipeToRefresh.isRefreshing = false
        }

        userAdapter = UserAdapter()
        binding.rvUserDetails.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

        userAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("UserDetails", it)
            }
            findNavController().navigate(
                R.id.action_userListFragment_to_UserDetailFragment,
                bundle
            )
        }

        setObservers()

        getUserData()
    }
    private fun setObservers() {
        userDetailsViewModel.userDetails.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    hideProgressBar()
                    dataState.data.let { userResponse ->
                        userAdapter.differ.submitList(userResponse)
                    }
                }

                is DataState.Error -> {
                    hideProgressBar()
                    context?.notifyUser("Error : ${dataState.message}")
                    logE("UserLIstFragment", "Error: ${dataState.message}")
                }

                is DataState.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun getUserData(){
        userDetailsViewModel.setStateEvent()
    }

    private fun hideProgressBar() {
        binding.pbUserDetails.visibility = View.GONE
        binding.userDetailsMain.visibility = View.VISIBLE
    }

    private fun showProgressBar() {
        binding.pbUserDetails.visibility = View.VISIBLE
        binding.userDetailsMain.visibility = View.GONE
    }
}