package com.bernovia.mylestone.ui.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ActivitySignUpBinding
import com.bernovia.mylestone.utils.PreferenceManager
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private var errorSnackbar: Snackbar? = null

    var preferenceManager: PreferenceManager = PreferenceManager.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this,R.layout.activity_sign_up)



        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        binding.signupButton.setOnClickListener {

            viewModel.signUp(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString(),binding.firstNameEditText.text.toString())


            viewModel.signUpResponseBody.observe(this, Observer { t ->
                Log.e("test", preferenceManager.accessToken)


            })


            viewModel.errorMessage.observe(this, Observer { errorMessage ->
                if (errorMessage != null) showError(errorMessage) else hideError()
            })

            binding.viewModel = viewModel

        }


    }

    private fun showError(@StringRes errorMessage: String) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}
