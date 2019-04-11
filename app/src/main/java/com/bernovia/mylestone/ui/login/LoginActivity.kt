package com.bernovia.mylestone.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ActivityLoginBinding
import com.bernovia.mylestone.ui.createMilestone.CreateMilestoneActivity
import com.bernovia.mylestone.ui.signUp.SignUpActivity
import com.bernovia.mylestone.utils.MylestoneUtil
import com.bernovia.mylestone.utils.PreferenceManager
import com.bernovia.mylestone.utils.TextWatcherAdapter
import com.bernovia.mylestone.utils.ValidateUtil
import com.google.android.material.snackbar.Snackbar


 class LoginActivity : AppCompatActivity(), View.OnClickListener,TextWatcherAdapter.TextWatcherListener {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private var errorSnackbar: Snackbar? = null

     companion object {
         lateinit var instance: LoginActivity
     }

     init {
         instance = this
     }


     var preferenceManager: PreferenceManager = PreferenceManager.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.emailEditText.addTextChangedListener(TextWatcherAdapter(binding.emailEditText, this))
        binding.passwordEditText.addTextChangedListener(TextWatcherAdapter(binding.passwordEditText, this))


        binding.signupTextView.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)

    }

    private fun showError(@StringRes errorMessage: String) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }


    private fun submit() {

        if  (!ValidateUtil.validateEmail(binding.emailEditText,binding.emailTextInputLayout,this))
            return

        if (!ValidateUtil.validatePassword(binding.passwordEditText,binding.passwordTextInputLayout,this))
            return


        viewModel.signIn(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())

        viewModel.loginResponseBody.observe(this, Observer {loginResponse->
            preferenceManager.userId=loginResponse.data.id
            val intent = Intent(this, CreateMilestoneActivity::class.java)
            startActivity(intent)
            finish()

        })


        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = viewModel



    }


    override fun onClick(v: View?) {
        val id = v!!.id

        when (id) {
            R.id.login_Button -> {
                MylestoneUtil.hideKeyboard(this)
                submit() }


           R.id.signup_TextView ->{
               val intent = Intent(this,SignUpActivity::class.java)
               startActivity(intent)

           }
        }

    }

    override fun onTextChanged(view: EditText, text: String) {
        val id = view.id
            when (id) {
                R.id.email_EditText -> ValidateUtil.validateEmail(binding.emailEditText,binding.emailTextInputLayout,this)
                R.id.password_EditText -> ValidateUtil.validatePassword(binding.passwordEditText,binding.passwordTextInputLayout,this)
            }


    }

}
