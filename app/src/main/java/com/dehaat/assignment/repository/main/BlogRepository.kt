package com.dehaat.assignment.repository.main

import com.dehaat.assignment.api.main.OpenApiMainService
import com.dehaat.assignment.persistence.BlogPostDao
import com.dehaat.assignment.repository.JobManager
import com.dehaat.assignment.session.SessionManager
import javax.inject.Inject

class BlogRepository
    @Inject
    constructor(
        val openApiMainService: OpenApiMainService,
        val blogPostDao: BlogPostDao,
        val sessionManager: SessionManager
    ) : JobManager("BlogRepository")
{

}