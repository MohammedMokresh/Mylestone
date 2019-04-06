package com.bernovia.mylestone.base

import com.bernovia.mylestone.injection.component.DaggerApplicationComponent
import com.bernovia.mylestone.utils.PreferenceManager
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.initializeInstance(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}
