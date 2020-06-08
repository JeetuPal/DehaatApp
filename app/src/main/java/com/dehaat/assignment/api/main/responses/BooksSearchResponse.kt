package com.dehaat.assignment.api.main.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BooksSearchResponse(

    @SerializedName("published_date")
    @Expose
    var publishedDate: String,

    @SerializedName("publisher")
    @Expose
    var publisher: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("description")
    @Expose
    var description: String,

    @SerializedName("price")
    @Expose
    var price: String
)