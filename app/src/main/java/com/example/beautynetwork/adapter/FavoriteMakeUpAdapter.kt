package com.example.beautynetwork.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.data.model.user.favorite.FavoriteMakeUp
import com.example.beautynetwork.databinding.ListItemFavoriteMakeUpBinding

class FavoriteMakeUpAdapter(
    private val dataset: List<FavoriteMakeUp>,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteMakeUpAdapter.FavoriteMakeUpViewHolder>() {

    inner class FavoriteMakeUpViewHolder(val binding: ListItemFavoriteMakeUpBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMakeUpViewHolder {
        val binding = ListItemFavoriteMakeUpBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteMakeUpViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: FavoriteMakeUpViewHolder, position: Int) {
        val item = dataset[position]

        holder.binding.title.text = item.name
        holder.binding.description.text = item.description
        holder.binding.imageView.load(item.image_link)

        val hCard = holder.binding.cardView

        // ScaleY = Stretchen oben unten           AnimationsTyp, Start, Ende
        val animator = ObjectAnimator.ofFloat(hCard, View.SCALE_Y, 0f, 1f)
        animator.duration = 800
        animator.start()

        holder.binding.cardView.setOnClickListener {
            showDeleteAlertDialog(item, holder.itemView.context)

            // RotationY = object horizontal rotieren                  Start, Ende
            val rotator = ObjectAnimator.ofFloat(hCard, View.ROTATION_Y, 0f, 360f)
            rotator.duration = 600

            // Animationen abspielen.
            val set = AnimatorSet()
            set.playTogether(rotator)
            set.start()
        }

    }

    private fun showDeleteAlertDialog(favoriteMakeUp: FavoriteMakeUp, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to delete this Make-Up?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes") { dialogInterface, which ->
            viewModel.myDeletedFavoriteMakeUp(favoriteMakeUp)
            Toast.makeText(context, "Deleted", Toast.LENGTH_LONG)
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
}