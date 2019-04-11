package com.bernovia.mylestone.utils

import android.app.Activity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import com.bernovia.mylestone.R
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

object ValidateUtil {


    fun validateEmail(editText: EditText, textInputLayout: TextInputLayout, activity: Activity): Boolean {
        val email = editText.text.toString().trim { it <= ' ' }

        if (email.isEmpty() || !isEmailValid(email)) {
            textInputLayout.error = activity.getString(R.string.email_not_valid)
            requestFocus(editText, activity)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }

        return true
    }


    fun validatePassword(editText: EditText, textInputLayout: TextInputLayout, activity: Activity): Boolean {
        if (editText.text.toString().trim { it <= ' ' }.isEmpty() || editText.text.toString().length < 6) {


            textInputLayout.error = activity.getString(R.string.password_error)
            requestFocus(editText, activity)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }

        return true
    }

    fun validateFieldsDidnotMatch(
        editText: EditText,
        textInputLayout: TextInputLayout,
        activity: Activity,
        editText1: EditText,
        errorMessage: String
    ): Boolean {
        if (editText.text.toString().trim { it <= ' ' }.isEmpty() || editText.text.toString() != editText1.text.toString()) {
            textInputLayout.error = errorMessage
            requestFocus(editText, activity)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }

        return true
    }


    fun validateEmptyFieldMinusFifty(
        editText: EditText,
        textInputLayout: TextInputLayout,
        activity: Activity,
        msg: String
    ): Boolean {
        if (editText.text.toString().trim { it <= ' ' }.isEmpty() || editText.text.toString().trim { it <= ' ' }.replace(
                " +".toRegex(),
                " "
            ).length < 50
        ) {
            textInputLayout.error = msg
            requestFocus(editText, activity)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }

        return true
    }


    fun validateEmptyField(
        editText: EditText,
        textInputLayout: TextInputLayout,
        activity: Activity,
        msg: String
    ): Boolean {
        if (editText.text.toString().trim { it <= ' ' }.isEmpty()) {
            textInputLayout.error = msg
            requestFocus(editText, activity)
            return false
        } else {
            textInputLayout.isErrorEnabled = false
        }

        return true
    }

    private fun requestFocus(view: View, context: Activity) {
        if (view.requestFocus()) {
            context.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    internal fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


}