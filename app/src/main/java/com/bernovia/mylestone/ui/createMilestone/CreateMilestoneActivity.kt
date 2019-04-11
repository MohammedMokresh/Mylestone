package com.bernovia.mylestone.ui.createMilestone

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ActivityCreateMilestoneBinding
import com.bernovia.mylestone.utils.*
import com.bernovia.mylestone.utils.DateUtil.updateDateLabel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class CreateMilestoneActivity : AppCompatActivity(), View.OnClickListener, TextWatcherAdapter.TextWatcherListener {


    private lateinit var binding: ActivityCreateMilestoneBinding
    private lateinit var viewModel: CreateMilestoneViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_milestone)

        binding.titleEditText.addTextChangedListener(TextWatcherAdapter(binding.titleEditText, this))
        binding.dateEditText.addTextChangedListener(TextWatcherAdapter(binding.dateEditText, this))
        binding.storyEditText.addTextChangedListener(TextWatcherAdapter(binding.storyEditText, this))
        binding.createButton.setOnClickListener(this)
        binding.backImageButton.setOnClickListener(this)
        binding.dateEditText.setOnClickListener(this)


        viewModel = ViewModelProviders.of(this).get(CreateMilestoneViewModel::class.java)




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
                binding.titleEditText,
                binding.titleTextInputLayout,
                this,
                resources.getString(R.string.title_error)
            )
        )
            return

        if (!ValidateUtil.validateEmptyField(
                binding.dateEditText,
                binding.dateTextInputLayout,
                this,
                resources.getString(R.string.date_error)
            )
        )
            return

        if (!ValidateUtil.validateEmptyField(
                binding.storyEditText,
                binding.storyTextInputLayout,
                this,
                resources.getString(R.string.story_error)
            )
        )
            return

        val checkBoxStatus: String = if (binding.makeItSecretCheckbox.isChecked) {
            "secret"

        } else {
            "open"

        }

        viewModel.createMilestone(
            binding.titleEditText.text.toString()
            , binding.dateEditText.text.toString(), binding.storyEditText.text.toString(), checkBoxStatus
        )


        viewModel.createMilestoneResoponseBody.observe(this, Observer {
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
            R.id.title_EditText -> ValidateUtil.validateEmptyField(
                binding.titleEditText,
                binding.titleTextInputLayout,
                this,
                resources.getString(R.string.title_error)
            )
            R.id.date_EditText -> ValidateUtil.validateEmptyField(
                binding.dateEditText,
                binding.dateTextInputLayout,
                this,
                resources.getString(R.string.date_error)
            )
            R.id.story_EditText -> ValidateUtil.validateEmptyField(
                binding.storyEditText,
                binding.storyTextInputLayout,
                this,
                resources.getString(R.string.story_error)
            )

        }

    }

    override fun onClick(v: View?) {
        val id = v!!.id

        when (id) {
            R.id.create_Button -> {
                MylestoneUtil.hideKeyboard(this)
                submit()}

            R.id.back_ImageButton -> finish()


            R.id.date_EditText -> {
                val myCalendar = Calendar.getInstance()
                val year = myCalendar.get(Calendar.YEAR)
                val month = myCalendar.get(Calendar.MONTH)
                val day = myCalendar.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, month)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    updateDateLabel(myCalendar,binding.dateEditText)
                }, year, month, day)

                dpd.datePicker.maxDate = System.currentTimeMillis() //Set max date
                DateUtil.openYearView(dpd.datePicker)
                dpd.setTitle("Select Date")
                dpd.show()
            }

        }


    }

}
