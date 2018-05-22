package com.supporter.marcus.classsupport.ui.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.supporter.marcus.classsupport.R
import org.koin.android.architecture.ext.viewModel
import android.arch.lifecycle.Observer
import android.os.Build
import android.text.Html
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import kotlinx.android.synthetic.main.fragment_proposal_detail.*
import androidx.navigation.fragment.NavHostFragment
import com.supporter.marcus.classsupport.util.ext.loadUrl


class ProposalDetailFragment : Fragment() {

    private val viewModel by viewModel<ProposalDetailViewModel>()
    var proposalId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        proposalId = ProposalDetailFragmentArgs.fromBundle(arguments).proposalId
        Log.d("ProposalID", proposalId)
        viewModel.getProposal(proposalId).observe(this, Observer { proposal ->
            showProposal(proposal!!)
        })
        return inflater.inflate(R.layout.fragment_proposal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProposal(proposalId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("HELOO", "ViewDestroyed")
        viewModel.getProposal(proposalId).removeObservers(this)
    }

    private fun showProposal(proposal: Proposal) {
        detail_proverty_level.text = proposal.povertyLevel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            detail_subject.text = Html.fromHtml(proposal.subject!!.name, Html.FROM_HTML_MODE_COMPACT)
        } else {
            detail_subject.text = Html.fromHtml(proposal.subject!!.name)

        }
        detail_grade_level.text = proposal.gradeLevel!!.name
        detail_amount.text = proposal.costToComplete
        progressBar1.progress = proposal.percentFunded!!.toInt()
        detail_website.setOnClickListener {
            val action = ProposalDetailFragmentDirections.next_action()
            action.setWeburl(proposal.proposalURL)
            NavHostFragment.findNavController(this).navigate(action)
        }
        detail_desc.text = proposal.shortDescription
        detail_title.text = proposal.title
        imageView.loadUrl(proposal.retinaImageURL!!)
        synopsis_webview.settings.defaultTextEncodingName = "utf-8"
        synopsis_webview.loadDataWithBaseURL(null, proposal.synopsis!!.replace("\\r\\n", "<br>").replace("\\n", "<br>").replace("&lt;", "<").replace("&gt;", ">"), "text/html; charset=utf-8", "utf-8", null)
        Log.d("synopsis", proposal.synopsis)
        Log.d("html", proposal.proposalURL)


    }


}
