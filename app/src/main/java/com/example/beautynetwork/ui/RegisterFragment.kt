package com.example.beautynetwork.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.databinding.FragmentRegisterBinding
import com.example.beautynetwork.ui.utils.Debug

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

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                showToast(message)
                viewModel.resetToastMessage()
            }
        }

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
            val username = binding.usernameRegister.text.toString()

            if (email != "" && password.length >= 12) {
                viewModel.register(email, password,username)

                findNavController().navigate(R.id.signInFragment)

            } else {
                if (username == "") {
                    binding.usernameRegister.error = "Enter your user name"

                }
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

    private fun showToast(text: String = Debug.TOAST_EMPTY_MESSAGE_SET.value) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}