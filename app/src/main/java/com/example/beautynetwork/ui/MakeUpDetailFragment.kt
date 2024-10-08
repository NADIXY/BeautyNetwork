package com.example.beautynetwork.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.databinding.FragmentMakeUpDetailBinding

class MakeUpDetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMakeUpDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakeUpDetailBinding.inflate(layoutInflater)

        binding.txtWebsiteLink.setOnClickListener {
            val urlIntent = Intent("android.intent.action.VIEW",
                Uri.parse("https://well.ca"))

            startActivity(urlIntent)
            Toast.makeText(requireContext(), "Loading 'well.ca' website", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadBeauty()

        viewModel.setSelectedProduct.observe(viewLifecycleOwner) {

            binding.txtTitle.text = it.name
            binding.txtDescription.text = it.description
            binding.imgProductView.load(it.image_link){
                error(R.drawable.spinner)
            }
            binding.txtCategory.text = it.category
            binding.txtPrice.text = "$${it.price} USD"
            binding.txtProductType.text = it.product_type
            binding.txtRating.text = "Rating ${it.rating.toString()}"
            binding.txtWebsiteLink.text = it.website_link
        }

        binding.backStack.setOnClickListener {
            findNavController().navigate(R.id.makeUpFragment)
        }

    }

}