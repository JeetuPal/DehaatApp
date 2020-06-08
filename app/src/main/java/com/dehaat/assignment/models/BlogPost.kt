package com.dehaat.assignment.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dehaat.assignment.api.main.responses.BooksSearchResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "blog_post")
data class BlogPost(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "pk")
    var pk: Int,

    @SerializedName("author_name")
    @Expose
    var authorName: String,

    @SerializedName("author_bio")
    @Expose
    var authorBio: String,

    @SerializedName("books")
    @Expose
    var books: List<BooksSearchResponse>

    ) {
    override fun toString(): String {
        return "BlogPost(pk=$pk, books=$books, authorName='$authorName', authorBio='$authorBio')"
    }
}