package com.example.beautynetwork.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.makeupapi.BeautyItem
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

        binding.imgProductView.setOnClickListener {

            binding.imgProductView.animate().apply {
                duration = 500
                rotationXBy(360f)
            }.withEndAction {
                binding.imgProductView.animate().apply {
                    duration = 200
                    rotationYBy(360f)
                }.start()
            }
        }

        if (holder is MakeUpAdapter.MyViewHolder) {

            holder.binding.imgProductView.load(item.image_link)
            holder.binding.txtTitle.text = item.name
            holder.binding.txtDescription.text = item.description
        }

        holder.binding.imgProductView.setOnClickListener {
            showSavedAlertDialog(item, holder.itemView.context)

        }

    }

    private fun showSavedAlertDialog(beautyItem: BeautyItem , context: Context?) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Save")
        builder.setMessage("Save?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.myFavoriteMakeUp(beautyItem)
            Toast.makeText(context, "The Make-Up has been saved", Toast.LENGTH_LONG)
                .show()
            dialogInterface.dismiss()
        }

        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG)
                .show()
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}