package com.example.beautynetwork.ui

import android.app.AlertDialog

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding.logoutFloatingActionButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Log out")
            builder.setMessage("Do you want to log out?")
            builder.setPositiveButton("Yes") { dialog, which ->
                viewModel.logout()
                findNavController().navigate(R.id.signInFragment)
                Toast.makeText(requireContext(), "You are logged out", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

         */

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

}