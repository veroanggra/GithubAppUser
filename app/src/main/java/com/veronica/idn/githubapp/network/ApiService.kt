package com.veronica.idn.githubapp.network

import com.veronica.idn.githubapp.BuildConfig
import com.veronica.idn.githubapp.data.model.DetailUser
import com.veronica.idn.githubapp.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?q=followers%3A>%3D1000&ref=searchresults&s=followers&type=Users")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    suspend fun getUser(): SearchResult

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    suspend fun getSearchUser(@Query("q") username: String): SearchResult

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.KEY}")
    suspend fun getDetailUser(@Path("username") username: String): DetailUser

}