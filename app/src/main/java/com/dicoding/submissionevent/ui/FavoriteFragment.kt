package com.dicoding.submissionevent.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionevent.R
import com.dicoding.submissionevent.data.response.ListEventsItem
import com.dicoding.submissionevent.databinding.FragmentFavoriteBinding
import com.dicoding.submissionevent.ui.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBar
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        favoriteAdapter = FavoriteAdapter(object : OnFavoriteClickListener {
            override fun onFavoriteClick(event: ListEventsItem) {

                val detailFragment = EventDetailFragment().apply {
                    arguments = Bundle().apply { putParcelable("event", event) }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }

            override fun onFavoriteLongClick(event: ListEventsItem) {

            }
        })


        binding.rvFavorites.layoutManager = LinearLayoutManager(context)
        binding.rvFavorites.adapter = favoriteAdapter



        viewModel.favoriteEvents.observe(viewLifecycleOwner, { events ->
            progressBar.visibility = View.GONE
            favoriteAdapter.submitList(events)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}