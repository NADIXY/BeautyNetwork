package com.example.beautynetwork.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.adapter.MakeUpAdapter
import com.example.beautynetwork.databinding.FragmentMakeUpBinding

class MakeUpFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMakeUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakeUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadBeauty()

        viewModel.beauty.observe(viewLifecycleOwner){
            binding.rvMakeUpList.adapter = MakeUpAdapter(it,viewModel)
        }

    }

}