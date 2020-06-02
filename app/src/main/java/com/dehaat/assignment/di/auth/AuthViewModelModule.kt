package com.dehaat.assignment.di.auth

import androidx.lifecycle.ViewModel
import com.dehaat.assignment.di.ViewModelKey
import com.dehaat.assignment.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

}