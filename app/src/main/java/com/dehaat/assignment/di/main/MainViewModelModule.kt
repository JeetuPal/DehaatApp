package com.dehaat.assignment.di.main

import androidx.lifecycle.ViewModel
import com.dehaat.assignment.di.ViewModelKey
import com.dehaat.assignment.ui.main.blog.BlogViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.dehaat.assignment.ui.main.account.AccountViewModel as AccountViewModel

@Module
abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BlogViewModel::class)
    abstract fun bindBlogViewModel(blogViewModel: BlogViewModel): ViewModel
}