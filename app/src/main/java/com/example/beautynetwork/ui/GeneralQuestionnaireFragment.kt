package com.example.beautynetwork.ui

import android.app.AlertDialog
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
import com.example.beautynetwork.data.model.user.GeneralQuestionnaire
import com.example.beautynetwork.databinding.FragmentGeneralQuestionnaireBinding

class GeneralQuestionnaireFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentGeneralQuestionnaireBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralQuestionnaireBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.generalQuestionnaireRef?.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                // Umwandeln des Snapshots in eine Klassen-Instanz von der Klasse Profil und setzen der Felder
                val myGeneralQuestionnaire = value.toObject(GeneralQuestionnaire::class.java)
                binding.tiet1.setText(myGeneralQuestionnaire?.question1)
                binding.tiet2.setText(myGeneralQuestionnaire?.question2)
                binding.tiet3.setText(myGeneralQuestionnaire?.question3)
                binding.tiet4.setText(myGeneralQuestionnaire?.question4)
                binding.tiet5.setText(myGeneralQuestionnaire?.question5)
            }
        }

        binding.btSave.setOnClickListener {
            val question1 = binding.tiet1.text.toString()
            val question2 = binding.tiet2.text.toString()
            val question3 = binding.tiet3.text.toString()
            val question4 = binding.tiet4.text.toString()
            val question5 = binding.tiet5.text.toString()

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Save")
            builder.setMessage("Do you want to save?")
            builder.setPositiveButton("Yes") { dialog, which ->

                if (question1 != "" && question2 != "" && question3 != "" && question4 != "" && question5 != "" ) {
                    val newGeneralQuestionnaire = GeneralQuestionnaire(question1, question2, question3, question4, question5)
                    viewModel.updateGeneralQuestionnaire(newGeneralQuestionnaire)
                    findNavController().navigate(R.id.generalQuestionnaire2Fragment)

                }
                Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Cancel") { dialog, which -> }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            findNavController().navigate(R.id.generalQuestionnaireFragment)
        }

    }
}