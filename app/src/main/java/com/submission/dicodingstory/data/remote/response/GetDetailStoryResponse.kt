package com.submission.dicodingstory.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.submission.dicodingstory.data.remote.response.child.Story

@Parcelize
data class GetDetailStoryResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("story")
	val story: Story? = null
) : Parcelable