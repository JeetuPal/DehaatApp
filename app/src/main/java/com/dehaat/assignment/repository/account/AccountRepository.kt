package com.dehaat.assignment.repository.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.dehaat.assignment.api.main.OpenApiMainService
import com.dehaat.assignment.models.AccountProperties
import com.dehaat.assignment.models.AuthToken
import com.dehaat.assignment.persistence.AccountPropertiesDao
import com.dehaat.assignment.repository.JobManager
import com.dehaat.assignment.repository.NetworkBoundResource
import com.dehaat.assignment.session.SessionManager
import com.dehaat.assignment.ui.DataState
import com.dehaat.assignment.ui.main.account.state.AccountViewState
import com.dehaat.assignment.util.ApiSuccessResponse
import com.dehaat.assignment.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepository
@Inject
constructor(
  val openApiMainService: OpenApiMainService,
  val accountPropertiesDao: AccountPropertiesDao,
  val sessionManager: SessionManager) : JobManager("AccountRepository")
{

  private val TAG: String = "AppDebug"

  fun getAccountProperties(authToken: AuthToken): LiveData<DataState<AccountViewState>> {
    return object: NetworkBoundResource<AccountProperties, AccountProperties, AccountViewState>(
      sessionManager.isConnectedToTheInternet(),
      true,
      true
    ){

      // if network is down, view the cache and return
      override suspend fun createCacheRequestAndReturn() {
        withContext(Dispatchers.Main){

          // finishing by viewing db cache
          result.addSource(loadFromCache()){ viewState ->
            onCompleteJob(DataState.data(viewState, null))
          }
        }
      }

      override suspend fun handleApiSuccessResponse(response: ApiSuccessResponse<AccountProperties>) {
        updateLocalDb(response.body)

        withContext(Dispatchers.Main){

          // finishing by viewing db cache
          result.addSource(loadFromCache()){ viewState ->
            onCompleteJob(DataState.data(viewState, null))
          }
        }
      }

      override fun loadFromCache(): LiveData<AccountViewState> {
        return accountPropertiesDao.searchByPk(authToken.account_pk!!)
          .switchMap {
            object: LiveData<AccountViewState>(){
              override fun onActive() {
                super.onActive()
                value = AccountViewState(it)
              }
            }
          }
      }

      override suspend fun updateLocalDb(cacheObject: AccountProperties?) {
        cacheObject?.let {
          accountPropertiesDao.updateAccountProperties(
            cacheObject.pk,
            cacheObject.email,
            cacheObject.username
          )
        }
      }

      override fun createCall(): LiveData<GenericApiResponse<AccountProperties>> {
        return openApiMainService
          .getAccountProperties(
            "Token ${authToken.token!!}"
          )
      }


      override fun setJob(job: Job) {
        addJob("getAccountProperties", job)
      }


    }.asLiveData()
  }
}