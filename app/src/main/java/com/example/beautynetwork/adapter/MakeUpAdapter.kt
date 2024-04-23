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
    private val items: List<BeautyItem>,
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
        val data = items[position]
        val binding = holder.binding

        binding.txtTitle.text = data.name
        binding.txtDescription.text = data.description
        //binding.txtPrice.text = "${data.price} €"
        binding.imgProductView.load(data.image_link)

        /*binding.beautyProducts.setOnClickListener {
            viewModel.setSelectedProduct(product)
            holder.binding.beautyProducts.findNavController().navigate(R.id.makeUpDetailFragment)
        }

         */

    }

    override fun getItemCount(): Int {
        return items.size
    }
}

