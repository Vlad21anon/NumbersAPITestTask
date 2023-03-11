package com.ukadovlad21.numbersapitesttask.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.ukadovlad21.numbersapitesttask.R
import com.ukadovlad21.numbersapitesttask.activity.MainActivity
import com.ukadovlad21.numbersapitesttask.utils.Resource

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by lazy {
        (activity as MainActivity).mainViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnGetFact).setOnClickListener {
            viewModel.getFactByNumber(1).observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideLoading()
                        view.findViewById<TextView>(R.id.tvResult).text = response.data.text


                    }
                    is Resource.Error -> {
                        hideLoading()
                        response.message.let { message ->
                            Toast.makeText(
                                activity,
                                "An error occurred: $message",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                    is Resource.Loading -> showLoading()


                }

            }
        }
        view.findViewById<Button>(R.id.btnGetRandomFact).setOnClickListener {
            viewModel.getRandomFact().observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideLoading()
                        view.findViewById<TextView>(R.id.tvResult).text = response.data.text

                    }
                    is Resource.Error -> {
                        hideLoading()
                        response.message.let { message ->
                            Toast.makeText(
                                activity,
                                "An error occurred: $message",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    is Resource.Loading -> showLoading()


                }

            }
        }

    }


    fun showLoading() {
        view?.findViewById<Button>(R.id.btnGetFact)?.isClickable = false
        view?.findViewById<Button>(R.id.btnGetRandomFact)?.isClickable = false
        view?.findViewById<ProgressBar>(R.id.pbLoading)?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        view?.findViewById<Button>(R.id.btnGetFact)?.isClickable = true
        view?.findViewById<Button>(R.id.btnGetRandomFact)?.isClickable = true
        view?.findViewById<ProgressBar>(R.id.pbLoading)?.visibility = View.INVISIBLE
    }
}