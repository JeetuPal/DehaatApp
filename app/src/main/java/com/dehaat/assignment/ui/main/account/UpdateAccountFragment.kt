package com.dehaat.assignment.ui.main.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.dehaat.assignment.R
import com.dehaat.assignment.models.AccountProperties
import com.dehaat.assignment.ui.DataState
import kotlinx.android.synthetic.main.fragment_update_account.*

class UpdateAccountFragment : BaseAccountFragment(){


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState->
            stateChangeListener.onDataStateChange(dataState)
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer {viewState->
            if (viewState != null)
            {
                viewState.accountProperties?.let {
                    Log.d(TAG, "UpdateAccountFragment, ViewState: ${it}")
                    setAccountDataFields(it)
                }
            }
        })
    }
    private fun setAccountDataFields(accountProperties: AccountProperties){
        if(input_email.text.isNullOrBlank()){
            input_email.setText(accountProperties.email)
        }
        if(input_username.text.isNullOrBlank()){
            input_username.setText(accountProperties.username)
        }
    }
}