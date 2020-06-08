package com.dehaat.assignment.api.main

import androidx.lifecycle.LiveData
import com.dehaat.assignment.models.AccountProperties
import com.dehaat.assignment.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface OpenApiMainService {

    @GET ("account/properties")
    fun getAccountProperties(
        @Header("Authorization") authorization: String
    ): LiveData<GenericApiResponse<AccountProperties>>
}