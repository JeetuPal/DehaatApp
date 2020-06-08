package com.dehaat.assignment.ui.main.blog.state

import com.dehaat.assignment.models.BlogPost

class BlogViewState(

    // BlogFragment vars
    var blogFields: BlogFields = BlogFields()

) {
    data class BlogFields(
        var blogList: List<BlogPost> = ArrayList<BlogPost>(),
        var searchQuery: String = ""
    )

}
