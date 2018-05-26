package com.supporter.marcus.classsupport.ui.search

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.NavHostFragment
import com.supporter.marcus.classsupport.R
import kotlinx.android.synthetic.main.search_filter_fragment.*
import org.koin.android.architecture.ext.sharedViewModel

class SearchFilter : Fragment() {

    companion object {
        fun newInstance() = SearchFilter()
    }

    private lateinit var spinnerGrade: Spinner
    private lateinit var spinnerSortby: Spinner
    private lateinit var spinnerSchoolType: Spinner

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
        setUpSchoolSpinner()

    }

    override fun onDetach() {
        super.onDetach()
        spinnerSchoolType.onItemSelectedListener = null
        spinnerSortby.onItemSelectedListener = null
        spinnerGrade.onItemSelectedListener = null
    }

    private fun setUpSchoolSpinner() {
        spinnerSchoolType = school_level_spinner
        spinnerSchoolType.adapter = ArrayAdapter(
                activity,
                R.layout.support_simple_spinner_dropdown_item,
                resources.getStringArray(R.array.school_options_array)
        )
        spinnerSchoolType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (spinnerSchoolType.selectedItem.toString()) {
                    "Charter School" -> viewModel.schoolType.postValue("1")
                    "Magnet School" -> viewModel.schoolType.postValue("4")
                    "Year Round School" -> viewModel.schoolType.postValue("5")
                    else -> viewModel.schoolType.postValue(null)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        }
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
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (spinnerSortby.selectedItem.toString()) {
                    "Urgency" -> viewModel.sortBy.postValue("0")
                    "Poverty" -> viewModel.sortBy.postValue("1")
                    "Cost To Complete" -> viewModel.sortBy.postValue("2")
                    "Expiration" -> viewModel.sortBy.postValue("5")
                    "Newest" -> viewModel.sortBy.postValue("7")
                    "Popularity" -> viewModel.sortBy.postValue("4")
                    else -> viewModel.sortBy.postValue(null)
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {

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
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        when (spinner_grade_level.selectedItem.toString()) {
                            "Grades PreK-2" -> viewModel.gradeType.postValue("1")
                            "Grades 3-5" -> viewModel.gradeType.postValue("2")
                            "Grades 6-8" -> viewModel.gradeType.postValue("3")
                            "High School" -> viewModel.gradeType.postValue("4")
                            "Adult Ed" -> viewModel.gradeType.postValue("5")
                            else -> viewModel.gradeType.postValue(null)
                        }
                    }


                    override fun onNothingSelected(parent: AdapterView<*>) {

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
                viewModel.sortBy.postValue(null)
                viewModel.schoolType.postValue(null)
                viewModel.gradeType.postValue(null)
                spinnerSchoolType.setSelection(0)
                spinnerGrade.setSelection(0)
                spinnerSortby.setSelection(0)
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
