package com.dehaat.assignment.api.main.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthorSearchResponse(

    @SerializedName("pk")
    @Expose
    var pk: Int,

    @SerializedName("books")
    @Expose
    var books: List<BooksSearchResponse>,

    @SerializedName("author_name")
    @Expose
    var authorName: String,

    @SerializedName("author_bio")
    @Expose
    var authorBio: String
)