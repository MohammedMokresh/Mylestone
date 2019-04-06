package com.bernovia.mylestone.ui.milestonesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ItemMilestoneBinding
import com.bernovia.mylestone.ui.milestonesList.model.Milestone


class MilestoneListAdapter: RecyclerView.Adapter<MilestoneListAdapter.ViewHolder>() {
    private lateinit var milestonesList:List<Milestone>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMilestoneBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_milestone, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(milestonesList[position])
    }

    override fun getItemCount(): Int {
        return if(::milestonesList.isInitialized) milestonesList.size else 0
    }

    fun updatePostList(postList:List<Milestone>){
        this.milestonesList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMilestoneBinding):RecyclerView.ViewHolder(binding.root){
        private val viewModel = MilestoneViewModel()

        fun bind(milestone: Milestone){
            viewModel.bind(milestone)
            binding.viewModel = viewModel
        }
    }
}