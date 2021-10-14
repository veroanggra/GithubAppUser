package com.veronica.idn.githubapp.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @field:SerializedName("total_count")
    val total_count: Int,

    @field:SerializedName("incomplete_results")
    val incomplete_results: Boolean,

    @field:SerializedName("items")
    val items: List<User>
)