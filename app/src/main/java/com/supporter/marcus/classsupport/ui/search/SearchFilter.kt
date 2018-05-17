package com.supporter.marcus.classsupport.ui.search

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.supporter.marcus.classsupport.R

class SearchFilter : Fragment() {

    companion object {
        fun newInstance() = SearchFilter()
    }

    private lateinit var viewModel: SearchFilterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_filter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchFilterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
