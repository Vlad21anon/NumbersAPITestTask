package com.ukadovlad21.numbersapitesttask.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ukadovlad21.numbersapitesttask.R
import com.ukadovlad21.numbersapitesttask.activity.DETAILS
import com.ukadovlad21.numbersapitesttask.activity.MainActivity
import com.ukadovlad21.numbersapitesttask.model.NumberData


class HistoryAdapter() : ListAdapter<NumberData, NumberItemHolder>(ItemComparator()) {
    class ItemComparator : DiffUtil.ItemCallback<NumberData>() {
        override fun areItemsTheSame(
            oldItem: NumberData,
            newItem: NumberData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: NumberData,
            newItem: NumberData
        ): Boolean {
            return oldItem.number == newItem.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_history_item, parent, false)
        return NumberItemHolder(view)
    }

    override fun onBindViewHolder(holder: NumberItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class NumberItemHolder(container: View) : RecyclerView.ViewHolder(container) {
    fun bind(data: NumberData) {
        itemView.apply {
            this.findViewById<TextView>(R.id.tvNumber).text = data.number.toString()
            this.findViewById<TextView>(R.id.tvDetails).text = data.text
            setOnClickListener {
                val bundle = Bundle()
                bundle.putString(DETAILS, data.text)
                findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }
        }


    }
}
