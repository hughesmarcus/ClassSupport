package com.supporter.marcus.classsupport.ui.detail

import android.arch.lifecycle.Observer
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat.invalidateOptionsMenu
import android.support.v4.app.Fragment
import android.text.Html
import android.util.Log
import android.view.*
import androidx.navigation.fragment.NavHostFragment
import com.supporter.marcus.classsupport.R
import com.supporter.marcus.classsupport.data.remote.json.Proposal
import com.supporter.marcus.classsupport.ui.search.ProposalItem
import com.supporter.marcus.classsupport.util.ext.loadUrl
import kotlinx.android.synthetic.main.fragment_proposal_detail.*
import org.koin.android.architecture.ext.viewModel


class ProposalDetailFragment : Fragment() {

    private val viewModel by viewModel<ProposalDetailViewModel>()
    var proposalId = ""
    lateinit var proposalItem: Proposal
    var isFavorite: Boolean = false
    //  lateinit var menu: Menu

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        proposalId = ProposalDetailFragmentArgs.fromBundle(arguments).proposalId
        Log.d("ProposalID", proposalId)
        viewModel.getProposal(proposalId).observe(this, Observer { proposal ->
            showProposal(proposal!!)
            proposalItem = proposal
        })
        viewModel.getFavorite(proposalId).observe(this, Observer { favorite ->
            setFavorite(favorite)
        })
        return inflater.inflate(R.layout.fragment_proposal_detail, container, false)
    }

    private fun setFavorite(favorite: Boolean?) {
        isFavorite = favorite as Boolean
        invalidateOptionsMenu(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProposal(proposalId)
        viewModel.getFavorite(proposalId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.getProposal(proposalId).removeObservers(this)
        viewModel.getFavorite(proposalId).removeObservers(this)
    }

    private fun showProposal(proposal: Proposal) {
        detail_proverty_level.text = proposal.povertyLevel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            detail_subject.text = Html.fromHtml(proposal.subject!!.name, Html.FROM_HTML_MODE_COMPACT)
            detail_title.text = Html.fromHtml(proposal.title, Html.FROM_HTML_MODE_COMPACT)
        } else {
            detail_subject.text = Html.fromHtml(proposal.subject!!.name)
            detail_title.text = Html.fromHtml(proposal.title)


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
        imageView.loadUrl(proposal.retinaImageURL!!)
        synopsis_webview.settings.defaultTextEncodingName = "utf-8"
        synopsis_webview.loadDataWithBaseURL(null, proposal.synopsis!!.replace("\\r\\n", "<br>").replace("\\n", "<br>").replace("&lt;", "<").replace("&gt;", ">"), "text/html; charset=utf-8", "utf-8", null)
        Log.d("synopsis", proposal.synopsis)
        Log.d("html", proposal.proposalURL)


    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        // this.menu = menu!!
        inflater?.inflate(R.menu.menu_proposal_detail, menu)


    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        if (isFavorite) {
            menu!!.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_black)
        } else {
            menu!!.findItem(R.id.action_favorite).setIcon(R.drawable.ic_favorite_gone)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                if (isFavorite) {
                    viewModel.removeFavorite(ProposalItem.from(proposalItem))
                    isFavorite = false
                    item.setIcon(R.drawable.ic_favorite_gone)
                    //menu!!.findItem(R.id.action_favorite)


                } else {
                    viewModel.addFavorite(ProposalItem.from(proposalItem))
                    isFavorite = true
                    //menu!!.findItem(R.id.action_favorite).
                    item.setIcon(R.drawable.ic_favorite_black)

                }
                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }


}
