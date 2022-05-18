package com.example.mvvmg.retrofit

import com.example.mvvmg.models.GithubUser
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getGithubUsers(): Response<List<GithubUser>>
}