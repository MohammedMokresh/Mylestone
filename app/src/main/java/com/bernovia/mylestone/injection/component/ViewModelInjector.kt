package com.bernovia.mylestone.injection.component


import com.bernovia.mylestone.injection.module.NetworkModule
import com.bernovia.mylestone.ui.milestonesList.MilestoneListViewModel
import com.bernovia.mylestone.ui.login.LoginViewModel
import com.bernovia.mylestone.ui.signUp.SignUpViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param milestoneListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(milestoneListViewModel: MilestoneListViewModel)
    fun injectLogin(loginViewModel: LoginViewModel)
    fun injectSignUp(signUpViewModel: SignUpViewModel)


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}