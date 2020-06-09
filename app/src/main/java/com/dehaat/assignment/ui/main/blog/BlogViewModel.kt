package com.dehaat.assignment.ui.main.blog

import androidx.lifecycle.LiveData
import com.dehaat.assignment.api.main.responses.BooksSearchResponse
import com.dehaat.assignment.models.BlogPost
import com.dehaat.assignment.repository.main.BlogRepository
import com.dehaat.assignment.session.SessionManager
import com.dehaat.assignment.ui.BaseViewModel
import com.dehaat.assignment.ui.DataState
import com.dehaat.assignment.ui.main.blog.state.BlogStateEvent
import com.dehaat.assignment.ui.main.blog.state.BlogViewState
import com.dehaat.assignment.util.AbsentLiveData
import javax.inject.Inject

class BlogViewModel
@Inject
constructor(
    val sessionManager: SessionManager,
    val blogRepository: BlogRepository
) : BaseViewModel<BlogStateEvent, BlogViewState>() {
    override fun handleStateEvent(stateEvent: BlogStateEvent): LiveData<DataState<BlogViewState>> {
        when(stateEvent){

            is BlogStateEvent.BlogSearchEvent ->{
                return sessionManager.cachedToken.value?.let { authToken ->
                    blogRepository.searchBlogPosts(
                        authToken,
                        viewState.value!!.blogFields.searchQuery
                    )
                }?: AbsentLiveData.create()
            }

            is BlogStateEvent.None ->{
                return AbsentLiveData.create()
            }

            is BlogStateEvent.CheckAuthorOfBlogPost-> {
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): BlogViewState {
        return BlogViewState()
    }

    fun setQuery(query: String){
        val update = getCurrentViewStateOrNew()
        update.blogFields.searchQuery = query
        _viewState.value = update
    }

    fun setBlogListData(blogList: List<BlogPost>){
        val update = getCurrentViewStateOrNew()
        update.blogFields.blogList = blogList
        _viewState.value = update
    }

    fun setBooksList(booksList: List<BooksSearchResponse>){
        val update = getCurrentViewStateOrNew()
        update.viewBlogFields.booksList = booksList
        _viewState.value = update
    }

    fun cancelActiveJobs(){
        blogRepository.cancelActiveJobs() // cancel active jobs
        handlePendingData() // hide progress bar
    }

    fun handlePendingData(){
        setStateEvent(BlogStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}