package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.Observer
import android.content.Context
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.navigation.fragment.NavHostFragment
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.supporter.marcus.classsupport.R
import com.supporter.marcus.classsupport.util.ext.gone
import com.supporter.marcus.classsupport.util.ext.visible
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel


class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private val filterViewModel: SearchFilterViewModel by sharedViewModel()
    private lateinit var searchView: MaterialSearchView
    var loading: Boolean = false
    private lateinit var spinner: ProgressBar
    private var searchQuery: String? = "Yoga"
    private var new: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
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
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        proposalList.clearOnScrollListeners()
        viewModel.states.removeObservers(this)
        viewModel.events.removeObservers(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchQuery = viewModel.getQuery()

        searchView = activity?.findViewById<MaterialSearchView>(R.id.search_view) ?: return
        spinner = progressbar_search
        initsearchview()
        if (new) {
            viewModel.loadNewProposals(filterViewModel.searched.value, filterViewModel.gradeType.value,
                    filterViewModel.schoolType.value,
                    filterViewModel.state.value, filterViewModel.sortBy.value,
                    null, "25")
            new = false
        }
        prepareListView()

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
                searchQuery = query
                filterViewModel.searched.postValue(query)
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
        viewModel.loadNewProposals(query, filterViewModel.gradeType.value,
                filterViewModel.schoolType.value, filterViewModel.state.value,
                filterViewModel.sortBy.value, null, "25")
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
        val action = SearchFragmentDirections.next_action()
        action.setProposalId(resultItem.id)
        NavHostFragment.findNavController(this).navigate(action)
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