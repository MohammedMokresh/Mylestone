package com.bernovia.mylestone.ui.milestonesList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ActivityMainBinding
import com.bernovia.mylestone.ui.createMilestone.CreateMilestoneActivity
import com.bernovia.mylestone.ui.login.LoginActivity
import com.bernovia.mylestone.utils.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.polyak.iconswitch.IconSwitch




class MainActivity : AppCompatActivity(), IconSwitch.CheckedChangeListener {



    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MilestoneListViewModel
    private var errorSnackbar: Snackbar? = null

    private var preferenceManager: PreferenceManager = PreferenceManager.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.milestonesRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(MilestoneListViewModel::class.java)



        if (preferenceManager.accessToken.equals("")||preferenceManager.accessToken==null ){
            binding.iconSwitch.visibility=View.GONE
        }else{
            binding.iconSwitch.visibility=View.VISIBLE
        }


        binding.iconSwitch.setCheckedChangeListener(this)


//        binding.loadPersonalSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                viewModel.loadPersonalMilestones()
//
//            } else {
//                viewModel.loadMilestones()
//            }
//        }


        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })


        binding.viewModel = viewModel


        binding.fab.setOnClickListener {
            if (preferenceManager.accessToken.equals("") || preferenceManager.accessToken == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, CreateMilestoneActivity::class.java)
                startActivity(intent)
            }


        }
    }

    override fun onCheckChanged(current: IconSwitch.Checked?) {
        if (current == IconSwitch.Checked.LEFT) {
            viewModel.loadMilestones()
        } else  if (current == IconSwitch.Checked.RIGHT){
            viewModel.loadPersonalMilestones()
        }
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }




}
