package com.supporter.marcus.classsupport.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.RadioGroup
import android.widget.Toast
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.miguelcatalan.materialsearchview.MaterialSearchView

import com.supporter.marcus.classsupport.R
import kotlinx.android.synthetic.main.search_filter_fragment.*
import org.koin.android.architecture.ext.sharedViewModel
import org.koin.android.architecture.ext.viewModel

class SearchFilter : Fragment() {

    companion object {
        fun newInstance() = SearchFilter()
    }

    private lateinit var radioGroup: RadioGroup

    // private val viewModel by sharedViewModel<SearchFilterViewModel>()
    private val viewModel: SearchFilterViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_filter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        radioGroup = radio_group_filter
        setupRadioGroup()
    }

    private fun setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radio_cost -> viewModel.setSortBY("2")
                R.id.radio_expiration -> viewModel.setSortBY("5")
                R.id.radio_newest -> viewModel.setSortBY("7")
                R.id.radio_proverty -> viewModel.setSortBY("1")
                R.id.radio_urgency -> viewModel.setSortBY("0")
                R.id.radio_popularity -> viewModel.setSortBY("4")
                else -> viewModel.setSortBY(null)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.filter_menu, menu)
        //val item = menu!!.findItem(R.id.action_search)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear -> {

                return true
            }
            R.id.action_apply -> {

                NavHostFragment.findNavController(this).navigate(R.id.next_action)
                Toast.makeText(activity, "hello", Toast.LENGTH_SHORT).show()


                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
