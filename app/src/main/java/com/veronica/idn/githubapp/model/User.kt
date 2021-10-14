package com.veronica.idn.githubapp.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @field:SerializedName("login")
    var login: String,

    @field:SerializedName("avatar_url")
    var avatar_url: String,
) : Parcelable
