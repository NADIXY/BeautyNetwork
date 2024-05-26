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
import com.example.beautynetwork.databinding.FragmentPasswordResetBinding

class PasswordResetFragment : Fragment() {

    private lateinit var binding: FragmentPasswordResetBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordResetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button um Passwort-Vergessen Mail zu senden
        // Email wird aus den Input-Feldern geholt
        // Wenn Email kein leerer String ist wird die sendPasswordResetFunktion im ViewModel aufgerufen
        binding.btSendEmailPasswordReset.setOnClickListener {
            val email = binding.tietEmailPasswordReset.text.toString()

            if (email != "") {
                viewModel.sendPasswordReset(email)

                findNavController().navigate(R.id.signInFragment)

            } else {
                if (email == "") {
                    binding.tietEmailPasswordReset.error = "Enter your email"

                }
            }

        }

        // Button um zurück zum SignInFragment zu navigieren
        binding.textBackToSignIn.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

    }
}