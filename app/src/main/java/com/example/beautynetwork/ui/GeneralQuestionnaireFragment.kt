package com.example.beautynetwork.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beautynetwork.ui.utils.DateUtils
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

        viewModel.generalQuestionnaireRef?.addSnapshotListener { snapshot, error ->
            snapshot?.let {
                // Umwandeln des Snapshots in eine Klassen-Instanz von der Klasse GeneralQuestionnaire und setzen der Felder
                val myGeneralQuestionnaire: List<GeneralQuestionnaire> =
                    snapshot.toObjects(GeneralQuestionnaire::class.java)
                Log.d(
                    "GeneralQuestionnaire", "${
                        myGeneralQuestionnaire.map { myGeneralQuestionnaire ->
                            "{${myGeneralQuestionnaire.question1} - ${myGeneralQuestionnaire.question2} - ${myGeneralQuestionnaire.question3} - " +
                                    "${myGeneralQuestionnaire.question4} - ${myGeneralQuestionnaire.question5} - ${myGeneralQuestionnaire.question6} -" +
                                    " ${myGeneralQuestionnaire.question7} - ${myGeneralQuestionnaire.question8} - ${DateUtils.toSimpleString(myGeneralQuestionnaire.timestamp.toDate())}}"
                        }
                    }"
                )
            }
        }


        binding.btSave.setOnClickListener {
            viewModel.setGeneralQuestionnaire(
                binding.tiet1.text.toString(),
                binding.tiet2.text.toString(),
                binding.tiet3.text.toString(),
                binding.tiet4.text.toString(),
                binding.tiet5.text.toString(),
                binding.tiet6.text.toString(),
                binding.tiet7.text.toString(),
                binding.tiet8.text.toString(),
            )

            binding.tiet1.setText("")
            binding.tiet2.setText("")
            binding.tiet3.setText("")
            binding.tiet4.setText("")
            binding.tiet5.setText("")
            binding.tiet6.setText("")
            binding.tiet7.setText("")
            binding.tiet8.setText("")

            findNavController().navigate(R.id.schedulerFragment)
        }

    }
}
