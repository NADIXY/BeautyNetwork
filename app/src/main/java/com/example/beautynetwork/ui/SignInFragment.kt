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
import com.example.beautynetwork.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    //Um das ViewModel zu initialisieren und zu verwalten.
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentSignInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button um User einzuloggen
        // Erst werden email und passwort aus den Eingabefeldern geholt
        // Wenn beide nicht leer sind rufen wir die login Funktion im ViewModel auf
        binding.signInBTN.setOnClickListener {
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()

            if (email != "" && password != "") {
                viewModel.login(email, password)
            }
        }

        // Button um zum RegisterFragment zu navigieren
        binding.registerBTN.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

        // Button um zum PasswordResetFragment zu navigieren
        binding.textPasswordReset.setOnClickListener {
            findNavController().navigate(R.id.passwordResetFragment)
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