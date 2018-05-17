package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.search_fragment.*
import com.supporter.marcus.classsupport.R
import org.koin.android.architecture.ext.viewModel
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.IBinder
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.supporter.marcus.classsupport.util.ext.gone
import com.supporter.marcus.classsupport.util.ext.visible


class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var searchView: MaterialSearchView
    var loading: Boolean = false
    private lateinit var spinner: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onPause() {
        super.onPause()
        proposalList.clearOnScrollListeners()

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = activity?.findViewById<MaterialSearchView>(R.id.search_view) ?: return
        spinner = progressbar_search
        initsearchview()
        viewModel.loadNewProposals("Yoga", null, null, null, null, null, "6")
        prepareListView()
        viewModel.states.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is SearchViewModel.AppendedProposalListState -> showAddedProposalsItemList(state.lasts.map {
                        ProposalItem.from(it)
                    } as MutableList<ProposalItem>)
                    is SearchViewModel.ProposalListState -> showProposalsItemList(state.lasts.map {
                        ProposalItem.from(it)
                    } as MutableList<ProposalItem>)
                }
            }
        })

        // Observe LoadingLocationEvent & LoadLocationFailedEvent
        viewModel.events.observe(this, Observer { event ->
            event?.let {
                when (event) {
                    is SearchViewModel.LoadingProposalsEvent -> loadingStart()
                    is SearchViewModel.LoadProposalsFailedEvent -> loadingFailed()
                    is SearchViewModel.LoadingProposalsEventEnded -> loadingEnd()
                }
            }
        })

//        view.findViewById<Button>(R.id.navigate_action_bt)?.setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.next_action, null)
//        )

    }

   private fun loadingFailed() {}

    private fun loadingEnd() {
        loading = false
        spinner.gone()
    }

    private fun loadingStart() {
        loading = true
        spinner.visible()
    }

    private fun initsearchview() {

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search(query)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                return false
            }
        })

        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
            }
        })
    }

    private fun search(query: String) {
        viewModel.loadNewProposals(query, null, null, null, null, null, "6")
    }

    private fun loadNextpage() {
        viewModel.loadNextPage()

    }

    private fun prepareListView() {
        proposalList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        proposalList.adapter = SearchListAdapter(
                mutableListOf(),
                ::onSearchItemSelected
        )

        proposalList.addOnScrollListener(recyclerViewOnScrollListener)

    }

    private fun onSearchItemSelected(resultItem: ProposalItem) {

    }

    private fun showAddedProposalsItemList(newList: MutableList<ProposalItem>) {
        val adapter: SearchListAdapter = proposalList.adapter as SearchListAdapter
        adapter.addAll(newList)
    }

    private fun showProposalsItemList(newList: MutableList<ProposalItem>) {
        val adapter: SearchListAdapter = proposalList.adapter as SearchListAdapter
        adapter.list = newList
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
        val item = menu!!.findItem(R.id.action_search)
        searchView = activity?.findViewById<MaterialSearchView>(R.id.search_view) ?: return
        searchView.setMenuItem(item)

    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = proposalList.childCount
            val totalItemCount = proposalList.adapter.itemCount
            val firstVisibleItemPosition = (proposalList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            if (!loading && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                loadNextpage()
                loading = true
            }

        }
    }

    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}