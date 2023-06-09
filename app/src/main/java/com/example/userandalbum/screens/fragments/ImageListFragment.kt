package com.example.userandalbum.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.userandalbum.R
import com.example.userandalbum.adapters.ImageAdapter
import com.example.userandalbum.databinding.FragmentImageListBinding
import com.example.userandalbum.util.DataState
import com.example.userandalbum.util.hide
import com.example.userandalbum.util.logE
import com.example.userandalbum.util.notifyUser
import com.example.userandalbum.util.show
import com.example.userandalbum.viewModels.ImageDetailsViewModel

class ImageListFragment : Fragment() {

    private lateinit var imageDetailsViewModel: ImageDetailsViewModel
    lateinit var imageAdapter: ImageAdapter
    private lateinit var binding: FragmentImageListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageDetailsViewModel = ViewModelProvider(this)[ImageDetailsViewModel::class.java]

        binding.swipeToRefresh.setOnRefreshListener {
            getUserData()
            binding.swipeToRefresh.isRefreshing = false
        }

        imageAdapter = ImageAdapter()
        binding.rvUserDetails.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

        imageAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("ImageDetails", it)
            }
            this.findNavController().navigate(
                R.id.action_imageListFragment_to_ImageDetailFragment,
                bundle
            )
        }

        setObservers()

        getUserData()
    }

    private fun setObservers() {
        imageDetailsViewModel.imageDetails.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Success -> {
                    hideProgressBar()
                    dataState.data.let { imageResponse ->
                        imageAdapter.differ.submitList(imageResponse)
                    }
                }

                is DataState.Error -> {
                    hideProgressBar()
                    context?.notifyUser("Error : ${dataState.message}")
                    logE("ImageListFragment", "Error: ${dataState.message}")
                }

                is DataState.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun getUserData(){
        imageDetailsViewModel.setStateEvent()
    }

    private fun hideProgressBar() {
        binding.pbImageDetails.hide()
        binding.imageDetailsMain.show()
    }

    private fun showProgressBar() {
        binding.pbImageDetails.show()
        binding.imageDetailsMain.hide()
    }

}