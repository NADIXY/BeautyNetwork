package com.example.beautynetwork.ui

import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beautynetwork.ui.utils.DateUtils
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.user.Appointment
import com.example.beautynetwork.databinding.FragmentSchedulerBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.Filter
import java.util.Calendar

class SchedulerFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSchedulerBinding
    private val calendar: Calendar = Calendar.getInstance()
    private var selectedService = ""

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

        binding.menu.setOnClickListener {
            showPopupMenu(it)
        }

        viewModel.items.observe(viewLifecycleOwner) { services ->
            val items = services.map { it.title }
            val autoComplete = binding.serviceET

            val adapter = ArrayAdapter(requireContext(), R.layout.list_item,items)

            autoComplete.setAdapter(adapter)

            autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
                    adapterView, view, i, l ->

                selectedService = adapterView.getItemAtPosition(i).toString()
                Toast.makeText(requireContext(),"Item: $selectedService", Toast.LENGTH_SHORT).show()

            }

        }

        viewModel.appointmentRef?.where(Filter.equalTo("userId",viewModel.user.value?.uid )
        )?.addSnapshotListener { snapshot, error ->
            snapshot?.let {

                //Termine in Firestore werden angezeigt!

                val myAppointments: List<Appointment> = snapshot.toObjects(Appointment::class.java)
                Log.d(
                    "Appointment", "${
                        myAppointments.map { myAppointments ->
                            "{${myAppointments.professional} - ${myAppointments.date} - ${myAppointments.hour} - ${myAppointments.service} -" +
                            " ${DateUtils.toSimpleString(myAppointments.timestamp.toDate())}}"
                        }
                    }"
                )

                //Termine in SchedulerFragment werden angezeigt!

                val appointmentsText = StringBuilder()

                for (appointment in myAppointments) {
                    Log.d("APP_DEBUG", "appointment.userID:: ${appointment.userId}")
                    appointmentsText.append("Professional: ${appointment.professional},\nDate: ${appointment.date},\n" +
                            "Hour: ${appointment.hour},\nService: ${appointment.service}\n\n\n")

                }
                binding.appointmentsListTextView.text = appointmentsText.toString()
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

            when {

                selectedService == "" -> {
                    message(it, "Please choose a service", "#FF0000")

                }

                hour < "9:00" && hour > "21:00" -> {
                    message(it, "Medical Beauty Institut Nadia Baptista is closed ", "#FF0000")

                }

                nadiaBaptista.isChecked -> {
                    message(it, "An appointment has been scheduled", "#FF03DAC5")
                    viewModel.setAppointment("nadiaBaptista",date,hour,selectedService)

                }

                azubi1.isChecked -> {
                    message(it, "An appointment has been scheduled", "#FF03DAC5")
                    viewModel.setAppointment("azubi1",date,hour,selectedService)

                }

                azubi2.isChecked -> {
                    message(it, "An appointment has been scheduled", "#FF03DAC5")
                    viewModel.setAppointment("azubi2",date,hour,selectedService)

                }

                pratikant.isChecked -> {
                    message(it, "An appointment has been scheduled", "#FF03DAC5")
                    viewModel.setAppointment("pratikant",date,hour,selectedService)

                }

                else -> {
                    message(it, "Please choose a professional", "#FF0000")
                }

            }

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("PLEASE FILL OUT!")
            builder.setMessage("Please fill out the general questionnaire")
            builder.setPositiveButton("Changes?Fill in again!") { dialog, which ->
                findNavController().navigate(R.id.generalQuestionnaireFragment)
                Toast.makeText(requireContext(), "General Questionnaire", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Filled") { dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

    }

    private fun message(view: View, message: String, color: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(Color.parseColor(color))
        snackbar.setTextColor(Color.parseColor("#FFFFFF"))
        snackbar.show()
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(
            R.menu.scheduler_menu,
            popup.menu
        )
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.your_appointment -> {
                    findNavController().navigate(R.id.schedulerDetailFragment)
                    true

                }
                else -> true
            }
        }
        popup.show()
    }
}