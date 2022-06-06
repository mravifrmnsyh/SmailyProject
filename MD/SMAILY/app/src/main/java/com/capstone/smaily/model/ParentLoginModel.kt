package com.capstone.smaily.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ParentLoginModel(
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var accesstoken: String? = null
): Parcelable