package com.example.userandalbum.screens.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.userandalbum.databinding.FragmentImageDetailBinding

class ImageDetailFragment : Fragment() {

    private lateinit var binding: FragmentImageDetailBinding

    private val args: ImageDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            Glide.with(this@ImageDetailFragment).load("${args.ImageDetails.thumbnailUrl}").into(binding.ivImage)
        }
    }
}