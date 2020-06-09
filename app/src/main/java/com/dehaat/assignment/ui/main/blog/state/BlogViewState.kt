package com.dehaat.assignment.ui.main.blog.state

import com.dehaat.assignment.api.main.responses.BooksSearchResponse
import com.dehaat.assignment.models.BlogPost

class BlogViewState(

    // BlogFragment vars
    var blogFields: BlogFields = BlogFields(),

// ViewBlogFragment vars
    var viewBlogFields: ViewBlogFields = ViewBlogFields()

) {
    data class BlogFields(
        var blogList: List<BlogPost> = ArrayList<BlogPost>(),
        var searchQuery: String = ""
    )

    data class ViewBlogFields(
        var booksList: List<BooksSearchResponse> = ArrayList<BooksSearchResponse>(),
        var isAuthorOfBlogPost: Boolean = false
    )

}
