package com.bernovia.mylestone.ui.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.bernovia.mylestone.base.BaseViewModel
import com.bernovia.mylestone.network.ApiEndPoint
import com.bernovia.mylestone.network.MilestoneApi
import com.bernovia.mylestone.ui.login.loginModels.LoginErrorBody
import com.bernovia.mylestone.ui.login.loginModels.LoginRequestBody
import com.bernovia.mylestone.ui.login.loginModels.LoginResponseBody
import com.bernovia.mylestone.utils.PreferenceManager
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject


class LoginViewModel : BaseViewModel() {
    @Inject
    lateinit var milestoneApi: MilestoneApi
    private lateinit var subscription: Disposable
    val loginResponseBody: MutableLiveData<LoginResponseBody> = MutableLiveData()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<String> = MutableLiveData()

    private var preferenceManager: PreferenceManager = PreferenceManager.instance


     fun signIn(email: String, password: String) {

        subscription =
            milestoneApi.signIn(ApiEndPoint.CONTENT_TYPE, ApiEndPoint.ACCEPT, LoginRequestBody(email, password))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onLoginStart() }
                .subscribe({ response ->

                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        onLoginSuccess(response.body()!!)
                        getHeaders(response)
                    } else {

                        val gson = GsonBuilder().create()
                        try {
                            val mError = gson.fromJson(response.errorBody()!!.string(), LoginErrorBody::class.java)
                            onLoginError(mError)

                        } catch (e: IOException) {
                            // handle failure to read error
                        }


                    }
                    onLoginFinish()

                }, { onLoginFinish() })


    }


    private fun onLoginStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onLoginFinish() {
        loadingVisibility.value = View.GONE
    }


    private fun onLoginSuccess(loginResponseBody: LoginResponseBody) {
        this.loginResponseBody.value = loginResponseBody
    }


    private fun onLoginError(loginErrorBody: LoginErrorBody) {
        errorMessage.value = loginErrorBody.errors[0]

    }


    private fun getHeaders(response: Response<LoginResponseBody>) {
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