package com.veronica.idn.githubapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Parcelize
data class DetailUser(
    @PrimaryKey()
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    var login: String,
    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    var avatar_url: String?,
    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    var name: String?,
    @ColumnInfo(name = "company")
    @field:SerializedName("company")
    var company: String?,
    @ColumnInfo(name = "location")
    @field:SerializedName("location")
    var location: String?,
    @ColumnInfo(name = "public_repos")
    @field:SerializedName("public_repos")
    var public_repos: Int?,
    @ColumnInfo(name = "followers")
    @field:SerializedName("followers")
    var followers: Int?,
    @ColumnInfo(name = "following")
    @field:SerializedName("following")
    var following: Int?
) : Parcelable