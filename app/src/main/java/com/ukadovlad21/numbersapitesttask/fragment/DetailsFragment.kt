package com.ukadovlad21.numbersapitesttask.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.ukadovlad21.numbersapitesttask.R
import com.ukadovlad21.numbersapitesttask.activity.DETAILS

class DetailsFragment : Fragment(R.layout.fragment_details) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.tvNumberDetails).text = arguments?.getString(DETAILS)
        view.findViewById<Button>(R.id.btnBack).setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
        }

    }

}