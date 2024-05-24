package com.example.beautynetwork.ui

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.data.model.user.Appointment
import com.example.beautynetwork.databinding.FragmentSchedulerBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class SchedulerFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSchedulerBinding
    private val calendar: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSchedulerBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)

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
                            "{${myAppointments.professional} - ${myAppointments.date} - ${myAppointments.hour} - ${myAppointments.service}}"
                        }
                    }"
                )
            }
        }


        var year = binding.datePicker.year
        var day = binding.datePicker.dayOfMonth
        var month = binding.datePicker.month
        var date: String = (year + day + month).toString()

        var minute= binding.timePicker.minute
        var hour = binding.timePicker.hour.toString()

        val datePicker = binding.datePicker
        datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_YEAR, dayOfMonth)

            var day = dayOfMonth.toString()
            val month: String

            if (dayOfMonth < 10) {
                day = "0$dayOfMonth"
            }
            if (monthOfYear < 10) {
                month = "" + (monthOfYear + 1)
            } else {
                month = (monthOfYear + 1).toString()

            }

            date = "$day / $month / $year"

        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->

            val min: String

            if (minute < 10) {
                min = "0$minute"

            } else {
                min = minute.toString()
            }

            hour = "$hourOfDay: $min"
        }

        binding.timePicker.setIs24HourView(true)


        binding.appointmentBt.setOnClickListener {
            val nadiaBaptista = binding.nadiaBaptista
            val azubi1 = binding.azubi1
            val azubi2 = binding.azubi2
            val pratikant = binding.pratikant

            /*
            val service1 = binding.service1
            val service2 = binding.service2
            val service3 = binding.service3
            val service4 = binding.service4
            val service5 = binding.service5
            val service6 = binding.service6
             */

            when {

                hour < "9:00" && hour > "21:00" -> {
                    message(it, "Medical Beauty Institut Nadia Baptista is closed", "#FF0000")

                }

                nadiaBaptista.isChecked -> {
                    message(it, "An appointment has been scheduled", "#FF03DAC5")
                    viewModel.setAppointment("nadiaBaptista",date,hour,"service")


                }

                azubi1.isChecked -> {
                    message(it, "An appointment has been scheduled", "#FF03DAC5")
                    viewModel.setAppointment("azubi1",date,hour,"service")

                }

                azubi2.isChecked -> {
                    message(it, "An appointment has been scheduled", "#FF03DAC5")
                    viewModel.setAppointment("azubi2",date,hour,"service")

                }

                pratikant.isChecked -> {
                    message(it, "An appointment has been scheduled", "#FF03DAC5")
                    viewModel.setAppointment("pratikant",date,hour,"service")

                }

                else -> {
                    message(it, "Please choose a professional", "#FF0000")
                }

            }

        }

    }

    private fun message(view: View, message: String, color: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(color))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

}

