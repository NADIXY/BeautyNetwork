package com.example.beautynetwork.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.beautynetwork.MainViewModel
import com.example.beautynetwork.R
import com.example.beautynetwork.data.model.user.Profile
import com.example.beautynetwork.databinding.FragmentChatProfileBinding

class ChatProfileFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentChatProfileBinding

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                //uri enthält Verweis auf unser Bild, damit können wir weiterarbeiten
                viewModel.uploadProfilePicture(uri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Funktion um Bild vom Gerät auszuwählen
        // Startet den Ressource-Picker und zeigt uns alle Bilder auf dem Gerät an
        binding.ivProfilePicChat.setOnClickListener {
            getContent.launch("image/*")
        }

        // Snapshot Listener: Hört auf Änderungen in dem Firestore Document, das beobachtet wird
        // Hier: Referenz auf Profil wird beobachtet
        viewModel.profileRef?.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                // Umwandeln des Snapshots in eine Klassen-Instanz von der Klasse Profil und setzen der Felder
                val myChatProfile = value.toObject(Profile::class.java)
                binding.tietUsername.setText(myChatProfile?.username)
                binding.ivProfilePicChat.load(myChatProfile?.profilePicture) {
                    error(R.drawable.baseline_account_box_24)
                }

            }
        }

    }
}
