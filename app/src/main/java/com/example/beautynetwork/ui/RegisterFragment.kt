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
import com.example.beautynetwork.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button um zurück zum SignInFragment zu navigieren
        binding.backText.setOnClickListener {
            findNavController().navigate(R.id.signInFragment)
        }

        // Button um User zu registrieren
        // Erst werden email und passwort aus den Eingabefeldern geholt
        // Wenn beide nicht leer sind rufen wir die register Funktion im ViewModel auf
        binding.registerBt.setOnClickListener {
            val email = binding.emailRegister.text.toString()
            val password = binding.passwordRegister.text.toString()


            if (email != "" && password.length >= 12) {
                viewModel.register(email, password)

                findNavController().navigate(R.id.signInFragment)

            } else {
                if (email == "") {
                    binding.emailRegister.error = "Enter your email"

                }
                if (password.length < 12) {
                    // Fehlermeldung anzeigen
                    binding.passwordRegister.error = "Password must contain at least 12 characters"
                }
                // Vorgang abbrechen
                return@setOnClickListener
            }

        }

        // User LiveData aus dem ViewModel wird beobachtet
        // Wenn User nicht gleich null (also der User eingeloggt ist) wird zum HomeFragment navigiert
        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }

    }

}