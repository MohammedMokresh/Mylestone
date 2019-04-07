package com.bernovia.mylestone.network

import com.bernovia.mylestone.network.ApiEndPoint.ENDPOINT_CREATE_MILESTONE
import com.bernovia.mylestone.network.ApiEndPoint.ENDPOINT_DELETE_MILESTONE
import com.bernovia.mylestone.network.ApiEndPoint.ENDPOINT_GET_PERSONAL
import com.bernovia.mylestone.network.ApiEndPoint.ENDPOINT_SIGN_IN
import com.bernovia.mylestone.network.ApiEndPoint.SIGN_UP
import com.bernovia.mylestone.ui.createMilestone.createMilestoneModel.CreateMilestoneRequestBody
import com.bernovia.mylestone.ui.createMilestone.createMilestoneModel.CreateMilestoneResoponseBody
import com.bernovia.mylestone.ui.login.loginModels.LoginRequestBody
import com.bernovia.mylestone.ui.login.loginModels.LoginResponseBody
import com.bernovia.mylestone.ui.milestonesList.model.AllMilestonesResponseBody
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
    fun getMilestones(@Header("Content-Type") contentType: String, @Header("Accept") accept: String): Observable<AllMilestonesResponseBody>

    @GET(ENDPOINT_GET_PERSONAL)
    fun getPersonalMilestones(@Header("Content-Type") contentType: String, @Header("Accept") accept: String,
                              @Header("Access-Token") accessToken: String, @Header("Client") client: String,
                              @Header("Token-Type") tokenType: String, @Header("Expiry") expiry: String,
                              @Header("Uid") uid: String): Observable<AllMilestonesResponseBody>


    @POST(ENDPOINT_SIGN_IN)
    fun signIn(
        @Header("Content-Type") contentType: String, @Header("Accept") accept: String,
        @Body loginRequestBody: LoginRequestBody
    ): Single<Response<LoginResponseBody>>


    @POST(SIGN_UP)
    fun signUp(
        @Header("Content-Type") contentType: String, @Header("Accept") accept: String,
        @Body signUpRequestBody: SignUpRequestBody
    ): Single<Response<SignUpResponseBody>>



    @DELETE(ENDPOINT_DELETE_MILESTONE)
    fun deleteMilestone(
        @Header("Content-Type") contentType: String, @Header("Accept") accept: String,
        @Header("Access-Token") accessToken: String, @Header("Client") client: String,
        @Header("Token-Type") tokenType: String, @Header("Expiry") expiry: String,
        @Header("Uid") uid: String, @Path("Id") customerId: Int
    ): Single<Response<CreateMilestoneResoponseBody>>



    @POST(ENDPOINT_CREATE_MILESTONE)
    fun createMilestone(
        @Header("Content-Type") contentType: String, @Header("Accept") accept: String,
        @Header("Access-Token") accessToken: String, @Header("Client") client: String,
        @Header("Token-Type") tokenType: String, @Header("Expiry") expiry: String,
        @Header("Uid") uid: String,
        @Body createMilestoneRequestBody: CreateMilestoneRequestBody
    ): Single<Response<CreateMilestoneResoponseBody>>


}

