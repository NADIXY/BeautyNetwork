package com.example.beautynetwork.ui

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAboutBinding
    private var ourFontSize = 14f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backStack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        binding.imageView3.setOnClickListener {

            binding.imageView3.animate().apply {
                duration = 500
                rotationXBy(360f)
            }.withEndAction {
                binding.imageView3.animate().apply {
                    duration = 200
                    rotationYBy(360f)
                }.start()
            }
        }

        binding.btIncrease.setOnClickListener {
            ourFontSize +=4f
            binding.aboutTextContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, ourFontSize)

        }
        binding.btDecrease.setOnClickListener {
            ourFontSize -=4f
            binding.aboutTextContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, ourFontSize)

        }

    }

}


