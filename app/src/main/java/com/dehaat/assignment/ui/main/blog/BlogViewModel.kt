package com.dehaat.assignment.ui.main.blog

import androidx.lifecycle.LiveData
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
                return AbsentLiveData.create()
            }

            is BlogStateEvent.None ->{
                return AbsentLiveData.create()
            }
        }
    }

    override fun initNewViewState(): BlogViewState {
        return BlogViewState()
    }
}