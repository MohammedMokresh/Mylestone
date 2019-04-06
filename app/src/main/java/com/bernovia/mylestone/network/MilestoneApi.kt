package com.bernovia.mylestone.network

import com.bernovia.mylestone.ui.milestonesList.model.AllMilestonesResponseBody
import com.bernovia.mylestone.network.ApiEndPoint.ENDPOINT_CREATE_MILESTONE
import com.bernovia.mylestone.network.ApiEndPoint.ENDPOINT_SIGN_IN
import com.bernovia.mylestone.network.ApiEndPoint.SIGN_UP
import com.bernovia.mylestone.ui.login.loginModels.LoginRequestBody
import com.bernovia.mylestone.ui.login.loginModels.LoginResponseBody
import com.bernovia.mylestone.ui.signUp.signUpModels.SignUpRequestBody
import com.bernovia.mylestone.ui.signUp.signUpModels.SignUpResponseBody
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*


interface MilestoneApi {
    /**
     * Get the list of the pots from the API
     */
    @GET(ENDPOINT_CREATE_MILESTONE)
    fun getMilestones(@Header("Content-Type") contentType: String,@Header("Accept")  accept :String): Observable<AllMilestonesResponseBody>



    @POST(ENDPOINT_SIGN_IN)
    fun signIn(@Header("Content-Type") contentType: String,@Header("Accept")  accept :String,
              @Body loginRequestBody: LoginRequestBody): Single<Response<LoginResponseBody>>



    @POST(SIGN_UP)
    fun signUp(@Header("Content-Type") contentType: String,@Header("Accept")  accept :String,
               @Body signUpRequestBody: SignUpRequestBody ): Single <Response<SignUpResponseBody>>

}

