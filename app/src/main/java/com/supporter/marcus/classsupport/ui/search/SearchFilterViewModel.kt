package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.ViewModel
import com.supporter.marcus.classsupport.util.mvvm.RxViewModel
import com.supporter.marcus.classsupport.util.rx.SchedulerProvider

class SearchFilterViewModel : ViewModel() {
    private var lastGradeType: String? = null
    private var lastSchoolType: String? = null
    private var lastState: String? = null
    private var lastSortBy: String? = "4"

    fun getGradeList(): String? {
        return lastGradeType
    }

    fun getSchoolType(): String? {
        return lastSchoolType
    }

    fun getState(): String? {
        return lastState
    }

    fun getSortBY(): String? {
        return lastSortBy
    }

    fun setGradeList(grade: String) {
        lastGradeType = grade
    }

    fun setSchoolType(school: String) {
        lastSchoolType = school
    }

    fun setState(state: String) {
        lastState = state
    }

    fun setSortBY(sort: String?) {
        lastSortBy = sort
    }


}
