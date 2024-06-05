package com.example.beautynetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.management.SlidePics
import com.example.beautynetwork.databinding.SlideItemBinding

class SlidePicsAdapter(
    private val dataset: List<SlidePics>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<SlidePicsAdapter.ViewHolder>() {

    class ViewHolder(val binding: SlideItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SlideItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val slides = dataset[position]

        holder.binding.slideImageView.setImageResource(slides.imageId)

        holder.binding.slideCardView.setOnClickListener {
            holder.binding.slideCardView.findNavController().navigate(R.id.aboutFragment)

        }

    }

}