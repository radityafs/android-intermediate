package com.submission.dicodingstory.data.remote.body

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterBody (
    val name: String,
    val email: String,
    val password: String
) : Parcelable