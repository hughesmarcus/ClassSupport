package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.ViewModel

/**
 * Used to save and share filter options between SearchViewFragment and SearchFilter
 */
class SearchFilterViewModel : ViewModel() {
    private var gradeType: String? = null
    private var schoolType: String? = null
    private var state: String? = null
    private var sortBy: String? = "4"

    fun getGradeList(): String? {
        return gradeType
    }

    fun getSchoolType(): String? {
        return schoolType
    }

    fun getState(): String? {
        return state
    }

    fun getSortBY(): String? {
        return sortBy
    }

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
