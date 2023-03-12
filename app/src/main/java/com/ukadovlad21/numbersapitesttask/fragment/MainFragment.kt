package com.ukadovlad21.numbersapitesttask.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ukadovlad21.numbersapitesttask.R
import com.ukadovlad21.numbersapitesttask.activity.MainActivity
import com.ukadovlad21.numbersapitesttask.adapter.HistoryAdapter
import com.ukadovlad21.numbersapitesttask.utils.Resource

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by lazy {
        (activity as MainActivity).mainViewModel
    }

    private val rvHistory by lazy {
        view?.findViewById<RecyclerView>(R.id.rvHistory)
    }
    private val historyAdapter = HistoryAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRV()
        viewModel.getAllNumbersDataLiveData().observe(viewLifecycleOwner) {
            historyAdapter.submitList(it)
        }

        view.findViewById<Button>(R.id.btnGetRandomFact).setOnClickListener {
            viewModel.getRandomFact().observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideLoading()
                        view.findViewById<TextView>(R.id.tvResult).text = response.data.text
                        viewModel.saveNumberData(response.data)

                    }
                    is Resource.Error -> {
                        hideLoading()
                        response.message.let { message ->
                            Toast.makeText(
                                activity, "An error occurred: $message", Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    is Resource.Loading -> showLoading()


                }

            }
        }

        view.findViewById<Button>(R.id.btnGetFact).setOnClickListener {
            val findNumber = view.findViewById<EditText>(R.id.etNumber).text.toString()
            if (findNumber.isNotEmpty()) {
                viewModel.getFactByNumber(findNumber.toInt())
                    .observe(viewLifecycleOwner) { response ->
                        when (response) {
                            is Resource.Success -> {
                                hideLoading()
                                val item = response.data
                                view.findViewById<TextView>(R.id.tvResult).text = item.text
                                viewModel.saveNumberData(item)
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

            } else {
                view.findViewById<EditText>(R.id.etNumber).error = "Enter a number"
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

    fun setupRV() {
        rvHistory?.apply {
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}