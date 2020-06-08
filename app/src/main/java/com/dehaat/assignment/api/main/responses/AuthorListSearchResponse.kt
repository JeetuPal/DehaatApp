package com.dehaat.assignment.api.main.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthorListSearchResponse (

    @SerializedName("data")
    @Expose
    var data: List<AuthorSearchResponse>
)