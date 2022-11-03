package com.mte.asitakipsistemi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mte.asitakipsistemi.R
import kotlinx.android.synthetic.main.fragment_choice.*

class ChoiceFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCovid.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_choiceFragment_to_arrangeAppointmentFragment)
        }
        buttonKuduz.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_choiceFragment_to_arrangeAppointmentFragment)
        }
        buttonGrip.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_choiceFragment_to_arrangeAppointmentFragment)
        }
    }


}