package com.example.beautynetwork.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.adapter.ServicesAdapter
import com.example.beautynetwork.databinding.FragmentServicesBinding

class ServicesFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentServicesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.items.observe(viewLifecycleOwner) {
            binding.rvServicesList.adapter = ServicesAdapter(it, viewModel)
        }

        // Reagiert auf die Nutzereingabe
        /*binding.etUserInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // Die onTextChanged-Funktion wird während der Änderung aufgerufen
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            // Die afterTextChanged-Funktion wird nach der Änderung aufgerufen
            override fun afterTextChanged(s: Editable?) {
                viewModel.filterItems(binding.etUserInput.text.toString())

            }
        })*/


    }

}