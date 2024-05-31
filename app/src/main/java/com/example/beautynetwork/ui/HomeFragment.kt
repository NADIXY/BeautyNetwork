package com.example.beautynetwork.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.adapter.RecomendedAdapter
import com.example.beautynetwork.adapter.ServicesAdapter
import com.example.beautynetwork.adapter.SlidePicsAdapter
import com.example.beautynetwork.adapter.UserAdapter
import com.example.beautynetwork.data.model.user.Profile
import com.example.beautynetwork.databinding.FragmentHomeBinding
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    private var currentPageCurrency = 0
    private var timerCurrency: Timer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.webBt.setOnClickListener {
            val urlIntent = Intent("android.intent.action.VIEW",
                Uri.parse("https://www.ueberdiemanspricht.de/medical_beauty_institut_nadia_baptista-14828.html"))

            startActivity(urlIntent)
        }

        binding.treatwellBt.setOnClickListener {
            val urlIntent = Intent("android.intent.action.VIEW",
                Uri.parse("https://www.treatwell.de/ort/medical-beauty-institut-leopoldstrasse/"))

            startActivity(urlIntent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChatList()

        binding.textView14.text= "Welcome"


        binding.menu.setOnClickListener {
            showPopupMenu(it)
        }

        binding.account.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        viewModel.loadBeauty()
        viewModel.beauty.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = RecomendedAdapter(it,viewModel)

        }

        viewModel.slidePics.observe(viewLifecycleOwner) {
            binding.rvSlide.adapter = SlidePicsAdapter(it, viewModel)
            startAutoScrollCurrency()
        }

        viewModel.items.observe(viewLifecycleOwner) {
            binding.rvServicesList.adapter = ServicesAdapter(it, viewModel)
        }

        // Reagiert auf die Nutzereingabe
        binding.etUserInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // Die onTextChanged-Funktion wird während der Änderung aufgerufen
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            // Die afterTextChanged-Funktion wird nach der Änderung aufgerufen
            override fun afterTextChanged(s: Editable?) {
                viewModel.filterItems(binding.etUserInput.text.toString())

            }
        })

        binding.recommended.setOnClickListener {
            findNavController().navigate(R.id.makeUpFragment)

        }

    }

    private fun setupChatList() {
        viewModel.profileCollectionReference?.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                val userList = value.map { it.toObject(Profile::class.java) }.toMutableList()
                userList.removeAll { it.userId == viewModel.user.value!!.uid }
                binding.rvUsers.adapter = UserAdapter(userList, viewModel)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(
            R.menu.home_menu,
            popup.menu
        )
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.about -> {
                    findNavController().navigate(R.id.aboutFragment)
                    true

                }
                R.id.preicelist -> {
                    findNavController().navigate(R.id.priceListFragment)
                    true

                }
                R.id.impressum -> {
                    findNavController().navigate(R.id.impressumFragment)
                    true

                }
                R.id.close -> {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Close")
                    builder.setMessage("Are you sure you want to close?")
                    builder.setPositiveButton("Yes") { dialog, which ->
                        activity?.finish()
                        Toast.makeText(requireContext(), "closed", Toast.LENGTH_SHORT).show()
                    }
                    builder.setNegativeButton("Cancel") { dialog, which -> }
                    val dialog: AlertDialog = builder.create()
                    dialog.show()

                    true
                }
                else -> true
            }
        }
        popup.show()
    }

    //Die Funktion startet einen Timer, der alle 5 Sekunden das RecyclerView automatisch scrollt.
    private fun startAutoScrollCurrency() {
        timerCurrency = Timer() //Erstellt einen neuen Timer
        timerCurrency?.schedule(object : TimerTask() { //Der Timer wird gestartet und führt alle 5 sec. aus.
            override fun run() {//Definiert die Aktion, die alle 5 Sekunden ausgeführt werden soll.
                activity?.runOnUiThread { // Führt den Code auf dem UI-Thread aus.
                    //Überprüft, ob die aktuelle Seite gleich der Anzahl der slidePics ist und setzt sie gegebenenfalls auf 0 zurück
                    if (currentPageCurrency == viewModel.slidePics.value?.size) {
                        currentPageCurrency = 0
                    }
                    //Scrollt die RecyclerView zur aktuellen Position und erhöht dann
                    // die Position um eins für den nächsten Durchlauf des Timers.
                    binding.rvSlide.smoothScrollToPosition(currentPageCurrency++)
                }
            }
        }, 0, 5000) // Ändern Sie das Intervall nach Bedarf für die RecyclerView
    }

}