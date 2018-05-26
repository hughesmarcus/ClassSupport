package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

/**
 * Used to save and share filter options between SearchViewFragment and SearchFilter
 */
class SearchFilterViewModel : ViewModel() {
    val searched = MutableLiveData<String>()
    val gradeType = MutableLiveData<String>()
    val schoolType = MutableLiveData<String>()
    val state = MutableLiveData<String>()
    val sortBy = MutableLiveData<String>()



}
