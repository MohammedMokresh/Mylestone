package com.bernovia.mylestone.base

import com.bernovia.mylestone.R
import com.bernovia.mylestone.injection.component.DaggerApplicationComponent
import com.bernovia.mylestone.utils.PreferenceManager
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import com.crashlytics.android.Crashlytics
import com.google.android.gms.ads.MobileAds
import io.fabric.sdk.android.Fabric



class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.initializeInstance(this)
        Fabric.with(this, Crashlytics())
        MobileAds.initialize(this, resources.getString(R.string.ad_app_unit_id))
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent.builder().application(this).build()
        component.inject(this)

        return component
    }
}
