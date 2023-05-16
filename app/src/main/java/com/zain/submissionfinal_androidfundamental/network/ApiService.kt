package com.zain.submissionfinal_androidfundamental.network

import com.zain.submissionfinal_androidfundamental.response.DetailUserResponse
import com.zain.submissionfinal_androidfundamental.response.FollowersResponseItem
import com.zain.submissionfinal_androidfundamental.response.FollowingResponseItem
import com.zain.submissionfinal_androidfundamental.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?")
    fun getUser(
        @Query("q") q : String,
        ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<FollowersResponseItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponseItem>>
}