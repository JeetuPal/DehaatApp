package com.dehaat.assignment.di.auth

import com.dehaat.assignment.ui.auth.ForgotPasswordFragment
import com.dehaat.assignment.ui.auth.LauncherFragment
import com.dehaat.assignment.ui.auth.LoginFragment
import com.dehaat.assignment.ui.auth.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLauncherFragment(): LauncherFragment

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector()
    abstract fun contributeForgotPasswordFragment(): ForgotPasswordFragment

}