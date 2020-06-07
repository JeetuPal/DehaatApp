package com.dehaat.assignment.di.main

import com.dehaat.assignment.di.main.account.ChangePasswordFragment
import com.dehaat.assignment.ui.main.account.AccountFragment
import com.dehaat.assignment.ui.main.account.UpdateAccountFragment
import com.dehaat.assignment.ui.main.blog.BlogFragment
import com.dehaat.assignment.ui.main.blog.UpdateBlogFragment
import com.dehaat.assignment.ui.main.blog.ViewBlogFragment
import com.dehaat.assignment.ui.main.create_blog.CreateBlogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeBlogFragment(): BlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeAccountFragment(): AccountFragment

    @ContributesAndroidInjector()
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector()
    abstract fun contributeCreateBlogFragment(): CreateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateBlogFragment(): UpdateBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeViewBlogFragment(): ViewBlogFragment

    @ContributesAndroidInjector()
    abstract fun contributeUpdateAccountFragment(): UpdateAccountFragment
}