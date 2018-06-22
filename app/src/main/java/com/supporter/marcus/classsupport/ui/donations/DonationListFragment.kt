package com.supporter.marcus.classsupport.ui.donations


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.ProgressBar
import com.supporter.marcus.classsupport.R
import com.supporter.marcus.classsupport.data.local.models.Donation
import com.supporter.marcus.classsupport.ui.EmptyListState
import com.supporter.marcus.classsupport.util.ext.gone
import com.supporter.marcus.classsupport.util.ext.visible
import kotlinx.android.synthetic.main.fragment_donation_list.*
import org.koin.android.architecture.ext.viewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class DonationListFragment : Fragment() {


    private val viewModel by viewModel<DonationListViewModel>()
    private lateinit var spinner: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewModel.states.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is DonationListViewModel.DonationListState -> showProposalsItemList(state.list)
                    is EmptyListState -> showListEmpty()
                }
            }
        })

        // Observe LoadingLocationEvent & LoadLocationFailedEvent
        viewModel.events.observe(this, Observer { event ->
            event?.let {
                when (event) {
                    is DonationListViewModel.LoadingDonationsEvent -> loadingStart()
                    is DonationListViewModel.LoadDonationsFailedEvent -> loadingFailed()
                    is DonationListViewModel.LoadingDonationsEventEnded -> loadingEnd()
                }
            }
        })
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowTitleEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.title = "Donations"
        return inflater.inflate(R.layout.fragment_donation_list, container, false)
    }

    private fun showListEmpty() {
        no_results_text.visible()
        donation_list.gone()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.states.removeObservers(this)
        viewModel.events.removeObservers(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = progressbar_don
        viewModel.loadDonations()
        prepareListView()

    }


    private fun loadingFailed() {}

    private fun loadingEnd() {
        spinner.gone()
    }

    private fun loadingStart() {
        spinner.visible()
    }


    private fun prepareListView() {
        donation_list.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        donation_list.adapter = DonationListAdapter(
                mutableListOf(),
                ::onDonationSelected
        )


    }

    private fun onDonationSelected(donation: Donation) {
        //  val action = SearchFragmentDirections.next_action()
        // action.setProposalId(donation.id)
        // NavHostFragment.findNavController(this).navigate(action)

    }


    private fun showProposalsItemList(newList: List<Donation>) {
        val adapter: DonationListAdapter = donation_list.adapter as DonationListAdapter
        adapter.list = newList
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_donation_list, menu)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_donation -> {

                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }


}
