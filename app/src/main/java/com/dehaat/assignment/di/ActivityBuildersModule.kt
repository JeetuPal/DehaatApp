package com.dehaat.assignment.di

import com.dehaat.assignment.di.auth.AuthFragmentBuildersModule
import com.dehaat.assignment.di.auth.AuthModule
import com.dehaat.assignment.di.auth.AuthScope
import com.dehaat.assignment.di.auth.AuthViewModelModule
import com.dehaat.assignment.ui.auth.AuthActivity
import com.dehaat.assignment.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthModule::class, AuthFragmentBuildersModule::class, AuthViewModelModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}