package com.supporter.marcus.classsupport.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.*
import androidx.navigation.fragment.NavHostFragment

import com.supporter.marcus.classsupport.R
import kotlinx.android.synthetic.main.search_filter_fragment.*
import org.koin.android.architecture.ext.sharedViewModel

class SearchFilter : Fragment() {

    companion object {
        fun newInstance() = SearchFilter()
    }

    private lateinit var radioGroup: RadioGroup
    private lateinit var spinnerGrade: Spinner
    private lateinit var spinnerSortby: Spinner

    private val viewModel: SearchFilterViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.search_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSortSpinner()
        setUpGradeSpinner()

    }

    private fun setUpSortSpinner() {


        spinnerSortby = spinner_sortby
        spinnerSortby.adapter = ArrayAdapter(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sort_options_array)
        )
        /*set click listener*/
        spinnerSortby.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                when (spinnerSortby.selectedItem.toString()) {
                    "Urgency" -> viewModel.setSortBY("0")
                    "Poverty" -> viewModel.setSortBY("1")
                    "Cost" -> viewModel.setSortBY("2")
                    "Expiration" -> viewModel.setSortBY("5")
                    "Newest" -> viewModel.setSortBY("7")
                    "Popularity" -> viewModel.setSortBY("4")
                    else -> viewModel.setSortBY(null)
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                /*Do something if nothing selected*/
            }
        }
    }

    private fun setUpGradeSpinner() {
        spinnerGrade = spinner_grade_level
        spinnerGrade.adapter = ArrayAdapter(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                resources.getStringArray(R.array.grade_level_array)
        )
        /*set click listener*/
        spinnerGrade.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                        when (spinner_grade_level.selectedItem.toString()) {
                            "Grades PreK-2" -> viewModel.setGradeList("1")
                            "Grades 3-5" -> viewModel.setGradeList("2")
                            "Grades 6-8" -> viewModel.setGradeList("3")
                            "High School" -> viewModel.setGradeList("4")
                            "Adult Ed" -> viewModel.setGradeList("5")
                            else -> viewModel.setGradeList(null)
                        }
                    }


                    override fun onNothingSelected(parent: AdapterView<*>) {
                        /*Do something if nothing selected*/
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



                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
