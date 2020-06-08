package com.dehaat.assignment.di.main

import com.dehaat.assignment.api.main.OpenApiMainService
import com.dehaat.assignment.persistence.AccountPropertiesDao
import com.dehaat.assignment.persistence.AppDatabase
import com.dehaat.assignment.persistence.BlogPostDao
import com.dehaat.assignment.repository.main.AccountRepository
import com.dehaat.assignment.repository.main.BlogRepository
import com.dehaat.assignment.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideOpenApiMainService(retrofitBuilder: Retrofit.Builder): OpenApiMainService {
        return retrofitBuilder
            .build()
            .create(OpenApiMainService::class.java)
    }

    @MainScope
    @Provides
    fun provideAccountRepository(
        openApiMainService: OpenApiMainService,
        accountPropertiesDao: AccountPropertiesDao,
        sessionManager: SessionManager
    ): AccountRepository{
        return AccountRepository(
            openApiMainService,
            accountPropertiesDao,
            sessionManager
        )
    }

    @MainScope
    @Provides
    fun provideBlogPostDao(db: AppDatabase): BlogPostDao {
        return db.getBlogPostDao()
    }

    @MainScope
    @Provides
    fun provideBlogRepository(
        openApiMainService: OpenApiMainService,
        blogPostDao: BlogPostDao,
        sessionManager: SessionManager
    ) : BlogRepository{
        return BlogRepository(
            openApiMainService = openApiMainService,
            blogPostDao = blogPostDao,
            sessionManager = sessionManager
        )
    }

}