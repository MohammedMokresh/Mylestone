package com.bernovia.mylestone.ui.milestonesList

import androidx.lifecycle.MutableLiveData
import com.bernovia.mylestone.base.BaseViewModel
import com.bernovia.mylestone.ui.milestonesList.model.Milestone

class MilestoneViewModel : BaseViewModel() {

    private val milestoneDate = MutableLiveData<String>()
    private val milestoneStory = MutableLiveData<String>()
    private val milestoneTitle = MutableLiveData<String>()
    private val milestoneUsername = MutableLiveData<String>()

    fun bind(milestone: Milestone){
        milestoneDate.value=milestone.date
        milestoneStory.value=milestone.story
        milestoneTitle.value=milestone.title
        milestoneUsername.value=milestone.user_name
    }

    fun getMilestoneDate():MutableLiveData<String>{
        return milestoneDate
    }

    fun getMilestoneStory(): MutableLiveData<String> {
        return milestoneStory
    }

    fun getMilestoneTitle():MutableLiveData<String>{
        return milestoneTitle
    }

    fun getMilestoneUserName(): MutableLiveData<String> {
        return milestoneUsername
    }

}