package com.bernovia.mylestone.base

import androidx.lifecycle.ViewModel
import com.bernovia.mylestone.injection.component.DaggerViewModelInjector
import com.bernovia.mylestone.injection.component.ViewModelInjector
import com.bernovia.mylestone.injection.module.NetworkModule
import com.bernovia.mylestone.ui.milestonesList.MilestoneListViewModel
import com.bernovia.mylestone.ui.login.LoginViewModel
import com.bernovia.mylestone.ui.signUp.SignUpViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }


    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MilestoneListViewModel -> injector.inject(this)

            is LoginViewModel -> injector.injectLogin(this)

            is SignUpViewModel -> injector.injectSignUp(this)
        }
    }
}
