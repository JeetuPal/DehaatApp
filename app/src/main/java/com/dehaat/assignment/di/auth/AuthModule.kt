package com.dehaat.assignment.di.auth

import android.content.SharedPreferences
import com.dehaat.assignment.api.auth.OpenApiAuthService
import com.dehaat.assignment.persistence.AccountPropertiesDao
import com.dehaat.assignment.persistence.AuthTokenDao
import com.dehaat.assignment.repository.auth.AuthRepository
import com.dehaat.assignment.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AuthModule{

//    @Singleton
//    @Provides
//    fun provideSharedPreferences(application: Application): SharedPreferences {
//        return application.getSharedPreferences(PreferenceKeys.APP_PREFERENCES, Context.MODE_PRIVATE)
//    }
//
//    @Singleton
//    @Provides
//    fun provideSharedPrefsEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
//        return sharedPreferences.edit()
//    }

    @AuthScope
    @Provides
    fun provideOpenApiAuthService(retrofitBuilder: Retrofit.Builder): OpenApiAuthService {
        return retrofitBuilder
            .build()
            .create(OpenApiAuthService::class.java)
    }

    @AuthScope
    @Provides
    fun provideAuthRepository(
        sessionManager: SessionManager,
        authTokenDao: AuthTokenDao,
        accountPropertiesDao: AccountPropertiesDao,
        openApiAuthService: OpenApiAuthService,
        preferences: SharedPreferences,
        editor: SharedPreferences.Editor
        ): AuthRepository {
        return AuthRepository(
            authTokenDao,
            accountPropertiesDao,
            openApiAuthService,
            sessionManager,
            preferences,
            editor
        )
    }

}