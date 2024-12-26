package com.dicoding.submissionevent.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dicoding.submissionevent.data.response.ListEventsItem
import com.dicoding.submissionevent.databinding.EventDetailBinding
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.bumptech.glide.load.DataSource
import com.dicoding.submissionevent.database.FavoriteEvent
import com.dicoding.submissionevent.ui.EventDetailViewModel
import com.dicoding.submissionevent.ui.FavoriteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.dicoding.submissionevent.R

class EventDetailFragment : Fragment() {
    private var _binding: EventDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventDetailViewModel by viewModels()
    private val viewModelFavorite : FavoriteViewModel by viewModels()
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event: ListEventsItem? = arguments?.getParcelable("event", ListEventsItem::class.java)
        event?.let {
            viewModel.setEvent(it)
            checkIfFavorite(it.id)
        }

        viewModel.event.observe(viewLifecycleOwner) { observedEvent ->
            observedEvent?.let { showEventDetail(it) }
        }

        binding.fabFavorite.setOnClickListener {
            event?.let { listEventItem ->
                val favoriteEvent = FavoriteEvent(
                    id = listEventItem.id,
                    name = listEventItem.name,
                    mediaCover = listEventItem.mediaCover,
                    beginTime = listEventItem.beginTime,
                    endTime = listEventItem.endTime,
                    registrants = listEventItem.registrants,
                    description = listEventItem.description,
                    ownerName = listEventItem.ownerName,
                    quota = listEventItem.quota,
                    link = listEventItem.link
                )
                toggleFavorite(favoriteEvent)
            }
        }

    }

    private fun showEventDetail(event: ListEventsItem) {
        binding.apply {
            progressBar.visibility = View.VISIBLE

            Glide.with(this@EventDetailFragment)
                .load(event.mediaCover)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(imageLogo)

            tvName.text = event.name
            tvOwner.text = event.ownerName
            tvTime.text = event.beginTime
            tvQuota.text = "Sisa Kuota: ${event.quota - event.registrants}"

            tvDescription.text = Html.fromHtml(event.description, Html.FROM_HTML_MODE_COMPACT)

            btnRegister.setOnClickListener {
                val registrationLink = event.link
                if (!registrationLink.isNullOrEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(registrationLink)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    private fun toggleFavorite(event: FavoriteEvent) {
        val favoriteEvent = FavoriteEvent(
            id = event.id,
            name = event.name,
            mediaCover = event.mediaCover,
            beginTime = event.beginTime,
            endTime = event.endTime,
            registrants = event.registrants,
            description = event.description,
            ownerName = event.ownerName,
            quota = event.quota,
            link = event.link
        )
        if (isFavorite) {
            viewModelFavorite.removeFavorite(event.id)
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
            isFavorite = false
        } else {
            viewModelFavorite.addFavorite(favoriteEvent)
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            isFavorite = true
        }
    }

    private fun checkIfFavorite(eventId: Int) {
        viewModelFavorite.isFavorite(eventId) { result ->
            isFavorite = result
            if (isFavorite) {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.fabFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
