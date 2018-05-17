package com.supporter.marcus.classsupport.ui.search

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.supporter.marcus.classsupport.R
import android.support.v7.widget.CardView
import android.widget.ImageButton
import com.supporter.marcus.classsupport.util.ext.inflate
import com.supporter.marcus.classsupport.util.ext.loadUrl


class SearchListAdapter(
        var list: MutableList<ProposalItem>,
        private val onDetailSelected: (ProposalItem) -> Unit
) : RecyclerView.Adapter<SearchListAdapter.SearchResultHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        val view = parent.inflate(R.layout.item_proposal)
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
        private val image = item.findViewById<ImageButton>(R.id.proposal_image)


        fun display(
                proposal: ProposalItem,
                onClick: (ProposalItem) -> Unit
        ) {
            proposalItemLayout.setOnClickListener { onClick(proposal) }
            proposaldesc.text = proposal.desc
            teacher.text = proposal.teacher
            school.text = proposal.school
            image.loadUrl(proposal.imageUrl)


        }

    }
}