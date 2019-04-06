package com.bernovia.mylestone.injection.module

import com.bernovia.mylestone.ui.milestonesList.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [MainFragmentBindingModule::class])
    internal abstract fun bindMainActivity(): MainActivity
}
