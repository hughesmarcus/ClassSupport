package com.supporter.marcus.classsupport.ui.favorite


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.supporter.marcus.classsupport.R
import com.supporter.marcus.classsupport.ui.search.ProposalItem
import com.supporter.marcus.classsupport.ui.search.SearchFragmentDirections
import com.supporter.marcus.classsupport.ui.search.SearchListAdapter
import kotlinx.android.synthetic.main.fragment_favorites_list.*
import org.koin.android.architecture.ext.viewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritesListFragment : Fragment() {

    private val viewModel by viewModel<FavoritesViewModell>()
    // private lateinit var spinner: ProgressBar
    private var new: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewModel.states.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is FavoritesViewModell.ProposalListFavState -> showProposalsItemList(state.list)
                }
            }
        })

        // Observe LoadingLocationEvent & LoadLocationFailedEvent
        viewModel.events.observe(this, Observer { event ->
            event?.let {
                when (event) {
                    is FavoritesViewModell.LoadingProposalsEvent -> loadingStart()
                    is FavoritesViewModell.LoadProposalsFailedEvent -> loadingFailed()
                    is FavoritesViewModell.LoadingProposalsEventEnded -> loadingEnd()
                }
            }
        })
        return inflater.inflate(R.layout.fragment_favorites_list, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.states.removeObservers(this)
        viewModel.events.removeObservers(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // spinner = progressbar_search
        if (new) {
            viewModel.loadProposals()
            new = false
        }
        prepareListView()

    }


    private fun loadingFailed() {}

    private fun loadingEnd() {
        //spinner.gone()
    }

    private fun loadingStart() {
        //spinner.visible()
    }


    private fun prepareListView() {
        proposal_fav_list.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        proposal_fav_list.adapter = SearchListAdapter(
                mutableListOf(),
                ::onSearchItemSelected
        )


    }

    private fun onSearchItemSelected(resultItem: ProposalItem) {
        val action = SearchFragmentDirections.next_action()
        action.setProposalId(resultItem.id)
        NavHostFragment.findNavController(this).navigate(action)
    }


    private fun showProposalsItemList(newList: MutableList<ProposalItem>) {
        val adapter: SearchListAdapter = proposal_fav_list.adapter as SearchListAdapter
        adapter.list = newList
        adapter.notifyDataSetChanged()
    }


}
