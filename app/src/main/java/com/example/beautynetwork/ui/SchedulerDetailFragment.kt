package com.example.beautynetwork.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.user.Appointment
import com.example.beautynetwork.databinding.FragmentSchedulerDetailBinding
import com.google.firebase.firestore.Filter

class SchedulerDetailFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSchedulerDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchedulerDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backStack.setOnClickListener {
            findNavController().navigate(R.id.schedulerFragment)
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


        viewModel.appointmentRef?.where(Filter.equalTo("userId",viewModel.user.value?.uid )
             )?.addSnapshotListener { snapshot, error ->
            snapshot?.let {

                //Termine in SchedulerFragment werden angezeigt!

                val appointmentsList: List<Appointment> =
                    snapshot.toObjects(Appointment::class.java)

                val appointmentsText = StringBuilder()

                for (appointment in appointmentsList) {
                    Log.d("APP_DEBUG", "appointment.userID:: ${appointment.userId}")
                    appointmentsText.append("Professional: ${appointment.professional},\nDate: ${appointment.date},\n" +
                            "Hour: ${appointment.hour},\nService: ${appointment.service}\n\n\n")

                }
                binding.appointmentsListTextView.text = appointmentsText.toString()
            }
        }

    }

}