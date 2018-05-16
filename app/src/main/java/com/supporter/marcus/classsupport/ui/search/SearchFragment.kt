package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.search_fragment.*
import com.supporter.marcus.classsupport.R
import org.koin.android.architecture.ext.viewModel


class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadNewProposals("Yoga",null,null,null,null,"20","50")
        prepareListView()
        more.setOnClickListener { search() }
        viewModel.states.observe(this, Observer { state ->
                state?.let {
                when (state) {
                    is SearchViewModel.AppendedProposalListState -> showAddedProposalsItemList(state.lasts.map {
                        ProposalItem.from(
                                it
                        )
                    } as MutableList<ProposalItem>)
                    is SearchViewModel.ProposalListState -> showProposalsItemList(state.lasts.map {
                        ProposalItem.from(
                                it
                        )
                    } as MutableList<ProposalItem>)
                }
            }
        })

//        view.findViewById<Button>(R.id.navigate_action_bt)?.setOnClickListener(
//                Navigation.createNavigateOnClickListener(R.id.next_action, null)
//        )

    }

    private fun search(){
        viewModel.loadNextPage()
    }
    private fun prepareListView() {
        proposalList.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        proposalList.adapter = SearchListAdapter(
                activity!!,
                mutableListOf(),
                ::onSearchItemSelected
        )
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
    }
}