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
import com.example.beautynetwork.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.webBt.setOnClickListener {
            val urlIntent = Intent("android.intent.action.VIEW", Uri.parse("https://www.ueberdiemanspricht.de/medical_beauty_institut_nadia_baptista-14828.html"))

            startActivity(urlIntent)
        }

        binding.treatwellBt.setOnClickListener {
            val urlIntent = Intent("android.intent.action.VIEW", Uri.parse("https://www.treatwell.de/ort/medical-beauty-institut-leopoldstrasse/"))

            startActivity(urlIntent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
                    builder.setMessage("Do you want to close?")
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

}