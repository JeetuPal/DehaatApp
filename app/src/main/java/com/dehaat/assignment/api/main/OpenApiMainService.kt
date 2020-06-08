package com.dehaat.assignment.api.main

import androidx.lifecycle.LiveData
import com.dehaat.assignment.api.main.responses.AuthorListSearchResponse
import com.dehaat.assignment.models.AccountProperties
import com.dehaat.assignment.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OpenApiMainService {

    @GET ("account/properties")
    fun getAccountProperties(
        @Header("Authorization") authorization: String
    ): LiveData<GenericApiResponse<AccountProperties>>

    @GET("https://run.mocky.io/v3/a08e548f-3e0a-4f1e-8566-f1f964a7272f")
    fun searchListBlogPosts(
        @Header("Authorization") authorization: String,
        @Query("search") query: String
    ): LiveData<GenericApiResponse<AuthorListSearchResponse>>
}