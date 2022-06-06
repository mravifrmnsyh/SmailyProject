package com.capstone.smaily.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChildrenLoginModel(
    var message: String? = null,
    var accessToken: String? = null
): Parcelable