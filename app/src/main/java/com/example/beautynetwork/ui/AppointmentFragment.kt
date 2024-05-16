package com.example.beautynetwork.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.user.Appointment
import com.example.beautynetwork.databinding.FragmentAppointmentBinding
import androidx.navigation.fragment.findNavController

class AppointmentFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.appointmentRef?.addSnapshotListener { snapshot, error ->
            snapshot?.let {
                // Umwandeln des Snapshots in eine Klassen-Instanz von der Klasse Appointment und setzen der Felder
                val myAppointments: List<Appointment> =
                    snapshot.toObjects(Appointment::class.java)
                Log.d(
                    "Appointment", "${
                        myAppointments.map { myAppointments ->
                            "{${myAppointments.question1} - ${myAppointments.question2} - ${myAppointments.question3} -${myAppointments.question4}" +
                                    " - ${myAppointments.question5} - ${myAppointments.question6} - ${myAppointments.question7} - ${myAppointments.question8}}"
                        }
                    }"
                )
            }
        }

        binding.btSave.setOnClickListener {
            viewModel.setAppointment(
                binding.tiet1.text.toString(),
                binding.tiet2.text.toString(),
                binding.tiet3.text.toString(),
                binding.tiet4.text.toString(),
                binding.tiet5.text.toString(),
                binding.tiet6.text.toString(),
                binding.tiet7.text.toString(),
                binding.tiet8.text.toString(),
            )

            binding.tiet1.setText("")
            binding.tiet2.setText("")
            binding.tiet3.setText("")
            binding.tiet4.setText("")
            binding.tiet5.setText("")
            binding.tiet6.setText("")
            binding.tiet7.setText("")
            binding.tiet8.setText("")

            findNavController().navigate(R.id.homeFragment)
        }

    }
}