package com.submission.dicodingstory.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.submission.dicodingstory.data.remote.response.child.ListStoryItem

@Parcelize
data class GetStoryResponse(

    @field:SerializedName("listStory")
	val listStory: List<ListStoryItem?>? = null,

    @field:SerializedName("error")
	val error: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
) : Parcelable