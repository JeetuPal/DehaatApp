package com.dehaat.assignment.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.dehaat.assignment.R
import com.dehaat.assignment.ui.BaseActivity
import com.dehaat.assignment.ui.ResponseType
import com.dehaat.assignment.ui.auth.state.AuthStateEvent
import com.dehaat.assignment.ui.main.MainActivity
import com.dehaat.assignment.viewmodels.ViewModelProviderFactory
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), NavController.OnDestinationChangedListener{
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: AuthViewModel

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        viewModel.cancelActiveJobs()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)
        findNavController(R.id.auth_nav_host_fragment).addOnDestinationChangedListener(this)

        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        checkPreviousAuthUser()
    }

    private fun checkPreviousAuthUser() {
        viewModel.setStateEvent(AuthStateEvent.CheckPreviousAuthEvent())
    }

    private fun subscribeObservers(){

        viewModel.dataState.observe(this, Observer { dataState ->
            onDataStateChange(dataState)    //  send datastate to base activity and all the errors are handled from there
            dataState.data?.let { data ->
                data.data?.let { event ->
                    event.getContentIfNotHandled()?.let {
                        it.authToken?.let {
                            Log.d(TAG, "AuthActivity, DataState: ${it}")
                            viewModel.setAuthToken(it)
                        }
                    }
                }
            }
        })

        viewModel.viewState.observe(this, Observer{
            Log.d(TAG, "AuthActivity, subscribeObservers: AuthViewState: ${it}")
            it.authToken?.let{
                sessionManager.login(it)
            }
        })

        sessionManager.cachedToken.observe(this, Observer{ dataState ->
            Log.d(TAG, "AuthActivity, subscribeObservers: AuthDataState: ${dataState}")
            dataState.let{ authToken ->
                if(authToken != null && authToken.account_pk != -1 && authToken.token != null){
                    navMainActivity()
                }
            }
        })
    }

    fun navMainActivity(){
        Log.d(TAG, "navMainActivity: called.")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun displayProgressBar(bool: Boolean) {
        if (bool){
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.GONE
        }
    }

    override fun expandAppbar() {
        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(true)
    }
}

















