package com.example.beautynetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.makeupapi.BeautyItem
import com.example.beautynetwork.databinding.ListItemRecommendedBinding

class RecomendedAdapter(
    private val dataset: List<BeautyItem>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<RecomendedAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ListItemRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ListItemRecommendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.recomendedImageView.load(item.image_link){
            error(R.drawable.spinner)
        }

        binding.recomendedCardView.setOnClickListener {
            viewModel.setSelectedProduct(item)
            holder.binding.recomendedCardView.findNavController()
                .navigate(R.id.makeUpDetailFragment)
        }
    }

}