package com.bernovia.mylestone.ui.createMilestone

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.bernovia.mylestone.base.BaseViewModel
import com.bernovia.mylestone.network.ApiEndPoint
import com.bernovia.mylestone.network.MilestoneApi
import com.bernovia.mylestone.ui.createMilestone.createMilestoneModel.CreateMilestoneRequestBody
import com.bernovia.mylestone.ui.createMilestone.createMilestoneModel.CreateMilestoneResoponseBody
import com.bernovia.mylestone.utils.PreferenceManager
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import javax.inject.Inject

class CreateMilestoneViewModel : BaseViewModel() {

    @Inject
    lateinit var milestoneApi: MilestoneApi
    private lateinit var subscription: Disposable
    val createMilestoneResoponseBody: MutableLiveData<CreateMilestoneResoponseBody> = MutableLiveData()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private var preferenceManager: PreferenceManager = PreferenceManager.instance


    fun createMilestone(title: String, date: String, story: String, status: String) {

        subscription =
            milestoneApi.createMilestone(
                ApiEndPoint.CONTENT_TYPE,
                ApiEndPoint.ACCEPT,
                preferenceManager.accessToken!!,
                preferenceManager.client!!,
                preferenceManager.tokenType!!,
                preferenceManager.expiry!!,
                preferenceManager.uid!!
                ,
                CreateMilestoneRequestBody(date, status, story, title)
            )

                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onCreateMilestoneStart() }
                .subscribe({ response ->

                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        onCreateMilestoneSuccess(response.body()!!)
                    } else {

                        val gson = GsonBuilder().create()
                        try {
                            val mError =
                                gson.fromJson(response.errorBody()!!.string(), CreateMilestoneResoponseBody::class.java)
                            onCreateMilestoneError(mError)

                        } catch (e: Throwable) {
                            // handle failure to read error
                        }

                    }
                    onCreateMilestoneFinish()

                }, { onCreateMilestoneFinish() })


    }


    private fun onCreateMilestoneStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onCreateMilestoneFinish() {
        loadingVisibility.value = View.GONE
    }


    private fun onCreateMilestoneSuccess(createMilestoneResoponseBody: CreateMilestoneResoponseBody) {
        this.createMilestoneResoponseBody.value = createMilestoneResoponseBody
    }


    private fun onCreateMilestoneError(createMilestoneResoponseBody: CreateMilestoneResoponseBody) {
        errorMessage.value = createMilestoneResoponseBody.message

    }


    override fun onCleared() {
        super.onCleared()
        try {
            subscription.dispose()
        }catch (e: Exception){}}


}