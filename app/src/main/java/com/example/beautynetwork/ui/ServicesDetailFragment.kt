package com.example.beautynetwork.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.databinding.FragmentServicesDetailBinding

class ServicesDetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentServicesDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backStack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        viewModel.setSelectedServices.observe(viewLifecycleOwner){
            binding.imageDetailService.setImageResource(it.imageId)
            binding.textTitleDetailService.text = it.title
            binding.textDescriptionDetailService.text = it.description
            binding.textInfoDetailService.text = it.info
            binding.textPreisDetailService.text = it.preice

        }

    }

}