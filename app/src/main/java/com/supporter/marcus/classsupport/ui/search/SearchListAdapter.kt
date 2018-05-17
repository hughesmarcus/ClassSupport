package com.supporter.marcus.classsupport.ui.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.supporter.marcus.classsupport.R
import android.support.v7.widget.CardView


class SearchListAdapter(
        var list: MutableList<ProposalItem>,
        private val onDetailSelected: (ProposalItem) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.SearchResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_proposal, parent, false)
        return SearchResultHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
        holder.display(list[position], onDetailSelected)
    }

    fun addAll(items: List<ProposalItem>) {
        val currentItemCount =  itemCount
        list.addAll(items)
        notifyItemRangeInserted(currentItemCount, items.size)
    }
    override fun getItemCount() = list.size

    inner class SearchResultHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val proposalItemLayout = item.findViewById<CardView>(R.id.proposalItemLayout)

        private val proposaldesc = item.findViewById<TextView>(R.id.prop_desc)
        private val teacher = item.findViewById<TextView>(R.id.teacher_name)
        private val school = item.findViewById<TextView>(R.id.school)


        fun display(
                proposalmodel: ProposalItem,
                onClick: (ProposalItem) -> Unit
        ) {
            proposalItemLayout.setOnClickListener { onClick(proposalmodel) }
            proposaldesc.text = proposalmodel.desc
            teacher.text = proposalmodel.teacher
            school.text = proposalmodel.school


        }

    }
}