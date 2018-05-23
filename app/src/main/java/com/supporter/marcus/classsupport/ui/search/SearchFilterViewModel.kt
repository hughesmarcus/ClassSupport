package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Used to save and share filter options between SearchViewFragment and SearchFilter
 */
class SearchFilterViewModel : ViewModel() {
    val searched = MutableLiveData<String>()
    //  private var searched: String? = "Yoga"
    private var gradeType: String? = null
    private var schoolType: String? = null
    private var state: String? = null
    private var sortBy: String? = "4"

    fun getGradeList(): String? {
        return gradeType
    }

    //fun getSearch(): String? {
    //    return searched
    // }

    fun getSchoolType(): String? {
        return schoolType
    }

    fun getState(): String? {
        return state
    }

    fun getSortBY(): String? {
        return sortBy
    }

    //  fun setSearched(search: String?) {
    //      searched = search
    //  }
    fun setGradeList(grade: String?) {
        gradeType = grade
    }

    fun setSchoolType(school: String) {
        schoolType = school
    }

    fun setState(state: String) {
        this.state = state
    }

    fun setSortBY(sort: String?) {
        sortBy = sort
    }


}
