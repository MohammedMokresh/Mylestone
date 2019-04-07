package com.bernovia.mylestone.ui.signUp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ActivitySignUpBinding
import com.bernovia.mylestone.ui.createMilestone.CreateMilestoneActivity
import com.bernovia.mylestone.ui.login.LoginActivity
import com.bernovia.mylestone.utils.PreferenceManager
import com.bernovia.mylestone.utils.TextWatcherAdapter
import com.bernovia.mylestone.utils.ValidateUtil
import com.google.android.material.snackbar.Snackbar

class SignUpActivity : AppCompatActivity(), View.OnClickListener, TextWatcherAdapter.TextWatcherListener {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private var errorSnackbar: Snackbar? = null


    var preferenceManager: PreferenceManager = PreferenceManager.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        binding.signupButton.setOnClickListener(this)
        binding.signinTextView.setOnClickListener(this)
        binding.termsTextView.setOnClickListener(this)


    }

    private fun showError(@StringRes errorMessage: String) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }


    private fun submit() {

        if (!ValidateUtil.validateEmptyField(
                binding.userNameEditText,
                binding.userNameTextInputLayout,
                this,
                resources.getString(R.string.user_name_error)
            )
        )
            return

        if (!ValidateUtil.validateEmail(binding.emailEditText, binding.emailTextInputLayout, this))
            return

        if (!ValidateUtil.validatePassword(binding.passwordEditText, binding.passwordTextInputLayout, this))
            return

        if (!ValidateUtil.validateFieldsDidnotMatch(
                binding.confirmPasswordEditText, binding.confirmPasswordTextInputLayout, this
                , binding.confirmPasswordEditText, resources.getString(R.string.password_does_not_match)
            )
        )
            return


        viewModel.signUp(
            binding.emailEditText.text.toString(),
            binding.passwordEditText.text.toString(),
            binding.userNameEditText.text.toString()
        )

        viewModel.signUpResponseBody.observe(this, Observer {
            LoginActivity.instance.finish()
            val intent = Intent(this, CreateMilestoneActivity::class.java)
            startActivity(intent)
            finish()
        })


        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = viewModel


    }


    override fun onTextChanged(view: EditText, text: String) {
        val id = view.id
        when (id) {
            R.id.user_name_EditText -> ValidateUtil.validateEmptyField(
                binding.userNameEditText,
                binding.userNameTextInputLayout,
                this,
                resources.getString(R.string.user_name_error)
            )
            R.id.email_EditText -> ValidateUtil.validateEmail(binding.emailEditText, binding.emailTextInputLayout, this)
            R.id.password_EditText -> ValidateUtil.validatePassword(
                binding.passwordEditText,
                binding.passwordTextInputLayout,
                this
            )
            R.id.confirm_password_EditText -> ValidateUtil.validateFieldsDidnotMatch(
                binding.confirmPasswordEditText, binding.confirmPasswordTextInputLayout, this
                , binding.confirmPasswordEditText, resources.getString(R.string.password_does_not_match)
            )
        }
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        when (id) {
            R.id.signup_Button -> {
                submit()
            }

            R.id.signin_TextView -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.terms_TextView -> {


            }
        }

    }


}
