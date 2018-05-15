package com.supporter.marcus.classsupport.ui.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.supporter.marcus.classsupport.R

class SearchListAdapter(
        val context: Context,
        var list: List<ProposalItem>,
        private val onDetailSelected: (ProposalItem) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.SearchResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_proposal, parent, false)
        return SearchResultHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
        holder.display(list[position], context, onDetailSelected)
    }

    override fun getItemCount() = list.size

    inner class SearchResultHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val proposalItemLayout = item.findViewById<android.support.constraint.ConstraintLayout>(R.id.proposalItemLayout)

        private val proposaldesc = item.findViewById<TextView>(R.id.prop_desc)


        fun display(
                proposalmodel: ProposalItem,
                context: Context,
                onClick: (ProposalItem) -> Unit
        ) {
            proposalItemLayout.setOnClickListener { onClick(proposalmodel) }
            proposaldesc.text = proposalmodel.desc


        }

    }
}