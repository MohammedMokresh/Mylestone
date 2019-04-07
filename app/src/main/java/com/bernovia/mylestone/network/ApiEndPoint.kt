package com.bernovia.mylestone.network

object ApiEndPoint {

    val CONTENT_TYPE = "application/json"
    val ACCEPT = "application/json"

    const val SIGN_UP = "auth"

    const val ENDPOINT_SIGN_IN = "auth/sign_in"

    //create mile and get
    const val ENDPOINT_CREATE_MILESTONE = "milestones"


    // DELETE api/milestones/2
    const val ENDPOINT_DELETE_MILESTONE = "milestones/{Id}"

    // api/milestones?filter=personal » to get personal milestones
    const val ENDPOINT_GET_PERSONAL = "milestones?filter=personal"


    const val ENDPOINT_SIGN_OUT = "auth/sign_out"

}
