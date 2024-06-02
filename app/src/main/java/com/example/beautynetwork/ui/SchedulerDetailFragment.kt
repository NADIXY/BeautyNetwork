package com.example.beautynetwork.ui

import android.os.Bundle
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
import com.google.firebase.auth.FirebaseAuth

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

        viewModel.appointmentRef?.addSnapshotListener { snapshot, error ->
            snapshot?.let {

                //Hier werden Termine in SchedulerFragment angezeigt!
                val appointmentsList: List<Appointment> =
                    snapshot.toObjects(Appointment::class.java)

                val userId = getCurrentUserId() // Funktion zum Abrufen der aktuellen Benutzer-ID

                //Es wird gefiltert um sicherzustellen, dass nur die Termine des aktuellen Benutzers (userId) im SchedulerFragment anzeigt
                val userAppointments = appointmentsList.filter { it.userId == userId }

                val appointmentsText = StringBuilder()

                for (appointment in appointmentsList) {

                    appointmentsText.append("Professional: ${appointment.professional},\n Date: ${appointment.date},\n" +
                            " Hour: ${appointment.hour},\n Service: ${appointment.service}\n\n\n")


                }
                binding.appointmentsListTextView.text = appointmentsText.toString()
            }
        }

    }

    private fun getCurrentUserId(): String {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        return firebaseUser?.uid ?: ""

    }

}