package com.edwin.ticketmaster_searchapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.edwin.ticketmaster_searchapp.databinding.FragmentEventsListBinding
import com.edwin.ticketmaster_searchapp.view.adapter.EventsListAdapter
import com.edwin.ticketmaster_searchapp.viewmodel.EventsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsListFragment : Fragment() {

    lateinit var viewDataBinding: FragmentEventsListBinding
    private val viewModel: EventsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewDataBinding = FragmentEventsListBinding.inflate(inflater, container, false)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.lifecycleOwner = this
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        viewDataBinding.eventList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewDataBinding.eventList.adapter = EventsListAdapter()
    }

    private fun setupViewModel() {
        viewModel.loadEvents()
    }

}