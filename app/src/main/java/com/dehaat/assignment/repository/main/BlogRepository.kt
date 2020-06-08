package com.dehaat.assignment.repository.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.dehaat.assignment.api.main.OpenApiMainService
import com.dehaat.assignment.api.main.responses.AuthorListSearchResponse
import com.dehaat.assignment.models.AuthToken
import com.dehaat.assignment.models.BlogPost
import com.dehaat.assignment.persistence.BlogPostDao
import com.dehaat.assignment.repository.JobManager
import com.dehaat.assignment.repository.NetworkBoundResource
import com.dehaat.assignment.session.SessionManager
import com.dehaat.assignment.ui.DataState
import com.dehaat.assignment.ui.main.blog.state.BlogViewState
import com.dehaat.assignment.util.ApiSuccessResponse
import com.dehaat.assignment.util.Converters
import com.dehaat.assignment.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BlogRepository
    @Inject
    constructor(
        val openApiMainService: OpenApiMainService,
        val blogPostDao: BlogPostDao,
        val sessionManager: SessionManager
    ) : JobManager("BlogRepository")
{
    private val TAG: String = "AppDebug"


    fun searchBlogPosts(
        authToken: AuthToken,
        query: String
    ): LiveData<DataState<BlogViewState>> {
        return object:
            NetworkBoundResource<AuthorListSearchResponse, List<BlogPost>, BlogViewState>(
                sessionManager.isConnectedToTheInternet(),
                true,
                true
            )
        {
            override suspend fun handleApiSuccessResponse(
                response: ApiSuccessResponse<AuthorListSearchResponse>
            )
            {
                val blogPostList: ArrayList<BlogPost> = ArrayList()
                for(blogPostResponse in response.body.data){
                    blogPostList.add(
                        BlogPost(
                            pk = blogPostResponse.pk,
                            authorName = blogPostResponse.authorName,
                            authorBio = blogPostResponse.authorBio,
                            books = blogPostResponse.books
                        )
                    )
                }
                updateLocalDb(blogPostList)

                createCacheRequestAndReturn()
            }

            override fun createCall(): LiveData<GenericApiResponse<AuthorListSearchResponse>> {
                return openApiMainService.searchListBlogPosts(
                    "Token ${authToken.token!!}",
                    query = query
                )
            }

            override fun setJob(job: Job) {
                addJob("searchBlogPosts", job)
            }

            override suspend fun createCacheRequestAndReturn() {
                withContext(Dispatchers.Main){

                    // finishing by viewing db cache
                    result.addSource(loadFromCache()){ viewState ->
                        onCompleteJob(DataState.data(viewState, null))
                    }
                }
            }

            override fun loadFromCache(): LiveData<BlogViewState> {
                return blogPostDao.getAllBlogPosts()
                    .switchMap {
                        object: LiveData<BlogViewState>(){
                            override fun onActive() {
                                super.onActive()
                                value = BlogViewState(
                                    BlogViewState.BlogFields(
                                        blogList = it
                                    )
                                )
                            }
                        }
                    }
            }

            override suspend fun updateLocalDb(cacheObject: List<BlogPost>?) {
                // loop through list and update the local db
                if(cacheObject != null){
                    withContext(Dispatchers.IO) {
                        for(blogPost in cacheObject){
                            try{
                                // Launch each insert as a separate job to be executed in parallel
                                val j = launch {
                                    Log.d(TAG, "updateLocalDb: inserting blog: ${blogPost}")
                                    blogPostDao.insert(blogPost)
                                }
                                j.join() // wait for completion before proceeding to next
                            }catch (e: Exception){
                                Log.e(TAG, "updateLocalDb: error updating cache data on blog post with author: ${blogPost.authorName}. " +
                                        "${e.message}")
                            }
                        }
                    }
                }
                else{
                    Log.d(TAG, "updateLocalDb: blog post list is null")
                }
            }

        }.asLiveData()
    }
}