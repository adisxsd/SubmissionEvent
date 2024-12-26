package com.dicoding.submissionevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.submissionevent.data.response.ListEventsItem
import com.dicoding.submissionevent.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.dicoding.submissionevent.R
import com.dicoding.submissionevent.databinding.CompletedBinding
import android.widget.ProgressBar
import androidx.fragment.app.viewModels

class CompletedEventsFragment : Fragment(), OnEventClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var progressBar: ProgressBar
    private var _binding: CompletedBinding? = null
    private val binding get() = _binding!!

    private val viewModel : completedEventsVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_completed_events)
        progressBar = view.findViewById(R.id.progressBar)
        eventAdapter = EventAdapter(this)
        recyclerView.adapter = eventAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.events.observe(viewLifecycleOwner) { events ->
            eventAdapter.submitList(events)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loadCompletedEvents()
    }


    override fun onEventClick(event: ListEventsItem) {
        val detailFragment = EventDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("event", event)
            }
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }
}
