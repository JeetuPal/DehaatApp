package com.dehaat.assignment.ui.main.account

import androidx.lifecycle.LiveData
import com.dehaat.assignment.models.AccountProperties
import com.dehaat.assignment.repository.account.AccountRepository
import com.dehaat.assignment.session.SessionManager
import com.dehaat.assignment.ui.BaseViewModel
import com.dehaat.assignment.ui.DataState
import com.dehaat.assignment.ui.main.account.state.AccountStateEvent
import com.dehaat.assignment.ui.main.account.state.AccountViewState
import com.dehaat.assignment.util.AbsentLiveData
import javax.inject.Inject

class AccountViewModel
@Inject
constructor(
    val sessionManager: SessionManager,
    val accountRepository: AccountRepository
) : BaseViewModel<AccountStateEvent, AccountViewState>() {
    override fun handleStateEvent(stateEvent: AccountStateEvent): LiveData<DataState<AccountViewState>> {
        when (stateEvent) {
            is AccountStateEvent.GetAccountPropertiesEvent -> {
                return sessionManager.cachedToken.value?.let { authToken ->
                    accountRepository.getAccountProperties(authToken)
                }?: AbsentLiveData.create()
            }
            is AccountStateEvent.UpdateAccountPropertiesEvent -> {
                return AbsentLiveData.create()
            }
            is AccountStateEvent.ChangePassWordEvent -> {
                return AbsentLiveData.create()
            }
            is AccountStateEvent.None -> {
                return AbsentLiveData.create()
            }
        }
    }

    fun setAccountPropertiesData(accountProperties: AccountProperties){
        val update = getCurrentViewStateOrNew()
        if(update.accountProperties == accountProperties){
            return
        }
        update.accountProperties = accountProperties
        _viewState.value = update
    }

    override fun initNewViewState(): AccountViewState {
        return AccountViewState()
    }

    fun logout(){
        sessionManager.logout()
    }

    fun cancelActiveJobs() {
        handlePendingData()
        accountRepository.cancelActiveJobs()
    }

    fun handlePendingData(){
        setStateEvent(AccountStateEvent.None())
    }

    override fun onCleared() {
        super.onCleared()
        cancelActiveJobs()
    }
}