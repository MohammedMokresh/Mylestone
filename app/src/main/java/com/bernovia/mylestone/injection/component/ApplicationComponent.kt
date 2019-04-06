package com.bernovia.mylestone.injection.component

import android.app.Application
import com.bernovia.mylestone.base.BaseApplication
import com.bernovia.mylestone.injection.module.ActivityBindingModule
import com.bernovia.mylestone.injection.module.ContextModule
import com.bernovia.mylestone.injection.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, NetworkModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: BaseApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}