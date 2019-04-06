package com.bernovia.mylestone.ui.signUp

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.bernovia.mylestone.base.BaseViewModel
import com.bernovia.mylestone.network.ApiEndPoint
import com.bernovia.mylestone.network.MilestoneApi
import com.bernovia.mylestone.ui.signUp.signUpModels.SignUpRequestBody
import com.bernovia.mylestone.ui.signUp.signUpModels.SignUpResponseBody
import com.bernovia.mylestone.utils.PreferenceManager
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject


class SignUpViewModel : BaseViewModel() {
    @Inject
    lateinit var milestoneApi: MilestoneApi
    private lateinit var subscription: Disposable
    val signUpResponseBody: MutableLiveData<SignUpResponseBody> = MutableLiveData()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private var preferenceManager: PreferenceManager = PreferenceManager.instance


    fun signUp(email: String, password: String,name:String) {

        subscription =
            milestoneApi.signUp(ApiEndPoint.CONTENT_TYPE, ApiEndPoint.ACCEPT, SignUpRequestBody(email, password,name))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onSignUpStart() }
                .subscribe({ response ->

                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        onSignUpSuccess(response.body()!!)
                        getHeaders(response)
                    } else {

                        val gson = GsonBuilder().create()
                        try {
                            val mError = gson.fromJson(response.errorBody()!!.string(), SignUpResponseBody::class.java)
                            onSignUpError(mError)

                        } catch (e: IOException) {
                            // handle failure to read error
                        }


                    }
                    onSignUpFinish()

                }, { onSignUpFinish() })


    }


    private fun onSignUpStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onSignUpFinish() {
        loadingVisibility.value = View.GONE
    }


    private fun onSignUpSuccess(signUpResponseBody: SignUpResponseBody) {
        this.signUpResponseBody.value = signUpResponseBody
    }


    private fun onSignUpError(signUpErrorBody: SignUpResponseBody) {
        errorMessage.value = signUpErrorBody.errors.full_messages[0]

    }


    private fun getHeaders(response: Response<SignUpResponseBody>) {
        preferenceManager.accessToken = response.headers().get("Access-Token")
        preferenceManager.client = response.headers().get("Client")
        preferenceManager.tokenType = response.headers().get("Token-Type")
        preferenceManager.expiry = response.headers().get("Expiry")
        preferenceManager.uid = response.headers().get("Uid")

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}