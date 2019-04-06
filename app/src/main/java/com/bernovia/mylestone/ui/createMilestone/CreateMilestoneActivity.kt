package com.bernovia.mylestone.ui.createMilestone

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ActivityCreateMilestoneBinding
import com.bernovia.mylestone.utils.PreferenceManager
import com.google.android.material.snackbar.Snackbar

class CreateMilestoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateMilestoneBinding
    private lateinit var viewModel: CreateMilestoneViewModel
    private var errorSnackbar: Snackbar? = null

    var preferenceManager: PreferenceManager = PreferenceManager.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_milestone)


        viewModel = ViewModelProviders.of(this).get(CreateMilestoneViewModel::class.java)



        binding.createButton.setOnClickListener {

            viewModel.createMilestone(binding.titleEditText.text.toString()
            ,"21/3/2019",binding.storyEditText.text.toString(),"open")

            //todo open,secret

            viewModel.createMilestoneResoponseBody.observe(this, Observer { t ->
//                Log.e("test", preferenceManager.accessToken)


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
