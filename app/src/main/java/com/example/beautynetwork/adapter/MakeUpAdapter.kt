package com.example.beautynetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.BeautyItem
import com.example.beautynetwork.databinding.ListItemMakeUpBinding

class MakeUpAdapter(
    private val dataset: List<BeautyItem>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<MakeUpAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ListItemMakeUpBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemMakeUpBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataset[position]
        val binding = holder.binding

        binding.txtTitle.text = item.name
        binding.txtDescription.text = item.description
        binding.imgProductView.load(item.image_link)

        binding.beautyProducts.setOnClickListener {
            viewModel.setSelectedProduct(item)
            holder.binding.beautyProducts.findNavController().navigate(R.id.makeUpDetailFragment)
        }

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}