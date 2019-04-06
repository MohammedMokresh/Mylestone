package com.bernovia.mylestone.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager private constructor(context: Context) {
    private val mPref: SharedPreferences
    /**
     * headers
     *
     */

    var accessToken: String?
        get() = mPref.getString(ACCESS_TOKEN, "")
        set(value) = mPref.edit().putString(ACCESS_TOKEN, value).apply()
    var client: String?
        get() = mPref.getString(CLIENT, "")
        set(value) = mPref.edit().putString(CLIENT, value).apply()
    var expiry: String?
        get() = mPref.getString(EXPIRY, "")
        set(value) = mPref.edit().putString(EXPIRY, value).apply()
    var uid: String?
        get() = mPref.getString(UID, "")
        set(value) = mPref.edit().putString(UID, value).apply()
    var tokenType: String?
        get() = mPref.getString(TOKEN_TYPE, "")
        set(value) = mPref.edit().putString(TOKEN_TYPE, value).apply()


    init {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }


    fun clear(): Boolean {
        return mPref.edit()
            .clear()
            .commit()
    }

    companion object {

        private val PREF_NAME = "com.mylestone.preference.PREF"
        private val ACCESS_TOKEN = "access_token"
        private val CLIENT = "client"
        private val EXPIRY = "expiry"
        private val UID = "uid"
        private val TOKEN_TYPE = "token_type"


        private var sInstance: PreferenceManager? = null

        @Synchronized
        fun initializeInstance(context: Context) {
            if (sInstance == null) {
                sInstance = PreferenceManager(context)
            }
        }

        val instance: PreferenceManager
            @Synchronized get() {
                if (sInstance == null) {
                    throw IllegalStateException(PreferenceManager::class.java.simpleName + " is not initialized, call initializeInstance(..) method first.")
                }
                return sInstance as PreferenceManager
            }
    }
}