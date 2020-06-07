package com.dehaat.assignment.di.main

import com.dehaat.assignment.api.main.OpenApiMainService
import com.dehaat.assignment.persistence.AccountPropertiesDao
import com.dehaat.assignment.repository.account.AccountRepository
import com.dehaat.assignment.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.lang.StringBuilder

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
}