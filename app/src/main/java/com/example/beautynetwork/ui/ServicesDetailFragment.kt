package com.example.beautynetwork.ui

import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Bundle
import android.service.chooser.ChooserAction
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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
    @RequiresApi(34)
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

        binding.btnShare.setOnClickListener {

            val sendIntent = Intent(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, "https://www.ueberdiemanspricht.de/medical_beauty_institut_nadia_baptista-14828.html")
            val shareIntent = Intent.createChooser(sendIntent, null)
            val customActions = arrayOf(
                ChooserAction.Builder(
                    Icon.createWithResource(context, R.drawable.logo),
                    "Nadia Baptista",
                    PendingIntent.getBroadcast(
                        context,
                        1,
                        Intent(Intent.ACTION_VIEW),
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
                    )
                ).build()
            )
            shareIntent.putExtra(Intent.EXTRA_CHOOSER_CUSTOM_ACTIONS, customActions)
            context?.startActivity(shareIntent)

        }
    }
}