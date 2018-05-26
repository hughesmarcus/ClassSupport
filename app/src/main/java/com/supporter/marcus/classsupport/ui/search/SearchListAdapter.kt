package com.supporter.marcus.classsupport.ui.search

import android.os.Build
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.supporter.marcus.classsupport.R
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
        private val image = item.findViewById<ImageView>(R.id.proposal_image)
        private val title = item.findViewById<TextView>(R.id.proposal_title)
        private val progressbar = item.findViewById<ProgressBar>(R.id.proposal_progressBar)
        private val stillNeeded = item.findViewById<TextView>(R.id.amount_needed)
        private val donors_num = item.findViewById<TextView>(R.id.donors_num)


        fun display(
                proposal: ProposalItem,
                onClick: (ProposalItem) -> Unit
        ) {
            proposalItemLayout.setOnClickListener { onClick(proposal) }
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                    proposaldesc.text = Html.fromHtml(proposal.desc, Html.FROM_HTML_MODE_COMPACT)
                    title.text = Html.fromHtml(proposal.title, Html.FROM_HTML_MODE_COMPACT)
                }
                else -> {
                    proposaldesc.text = Html.fromHtml(proposal.desc)
                    title.text = Html.fromHtml(proposal.title)
                }
            }
            teacher.text = proposal.teacher
            school.text = proposal.school

            image.loadUrl(proposal.imageUrl)
            progressbar.progress = proposal.prefunded.toInt()
            stillNeeded.text = "$" + proposal.costToComplete
            donors_num.text = proposal.donors

        }

    }
}