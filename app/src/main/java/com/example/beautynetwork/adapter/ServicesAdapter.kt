package com.example.beautynetwork.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.data.model.Services
import com.example.beautynetwork.databinding.ListItemServicesBinding

class ServicesAdapter(
    private val dataset: List<Services>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<ServicesAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ListItemServicesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            ListItemServicesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )

        )
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = dataset[position]
    }
}