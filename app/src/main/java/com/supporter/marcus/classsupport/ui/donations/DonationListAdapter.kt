package com.supporter.marcus.classsupport.ui.donations

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.supporter.marcus.classsupport.R
import com.supporter.marcus.classsupport.data.local.models.Donation
import com.supporter.marcus.classsupport.util.ext.inflate

class DonationListAdapter(
        var list: List<Donation>,
        private val onDetailSelected: (Donation) -> Unit
) : RecyclerView.Adapter<DonationListAdapter.DonationResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationResultHolder {
        val view = parent.inflate(R.layout.item_proposal)
        return DonationResultHolder(view)
    }

    override fun onBindViewHolder(holder: DonationResultHolder, position: Int) {
        holder.display(list[position], onDetailSelected)
    }


    override fun getItemCount() = list.size

    inner class DonationResultHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val donationItemLayout = item.findViewById<CardView>(R.id.donationItemLayout)
        private val donationName = item.findViewById<TextView>(R.id.name)
        private val donationAmount = item.findViewById<TextView>(R.id.amount)
        private val donationDate = item.findViewById<TextView>(R.id.date)



        fun display(
                donation: Donation,
                onClick: (Donation) -> Unit
        ) {
            donationItemLayout.setOnClickListener { onClick(donation) }
            donationName.text = donation.name
            donationAmount.text = "$ " + donation.amount.toString()
            donationDate.text = donation.date.toString()


        }

    }
}