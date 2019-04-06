package com.bernovia.mylestone.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ActivityLoginBinding
import com.bernovia.mylestone.utils.PreferenceManager
import com.google.android.material.snackbar.Snackbar


class LoginActivity : AppCompatActivity() ,View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var errorSnackbar: Snackbar? = null

    var preferenceManager: PreferenceManager = PreferenceManager.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.loginButton.setOnClickListener {

            viewModel.signIn(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())


            viewModel.loginResponseBody.observe(this, Observer { t ->
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

    override fun onClick(v: View?) {


        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
