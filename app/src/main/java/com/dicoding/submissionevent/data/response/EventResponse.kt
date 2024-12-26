package com.dicoding.submissionevent.data.response
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.SerializedName

data class EventResponse(

	@field:SerializedName("listEvents")
	val listEvents: List<ListEventsItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class ListEventsItem(
	@SerializedName("summary")
	val summary: String,

	@SerializedName("mediaCover")
	val mediaCover: String,

	@SerializedName("registrants")
	val registrants: Int,

	@SerializedName("imageLogo")
	val imageLogo: String,

	@SerializedName("link")
	val link: String,

	@SerializedName("description")
	val description: String,

	@SerializedName("ownerName")
	val ownerName: String,

	@SerializedName("cityName")
	val cityName: String,

	@SerializedName("quota")
	val quota: Int,

	@SerializedName("name")
	val name: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("beginTime")
	val beginTime: String,

	@SerializedName("endTime")
	val endTime: String,

	@SerializedName("category")
	val category: String
) : Parcelable


