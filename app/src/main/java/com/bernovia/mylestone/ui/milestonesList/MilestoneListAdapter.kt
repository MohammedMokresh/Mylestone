package com.bernovia.mylestone.ui.milestonesList

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bernovia.mylestone.R
import com.bernovia.mylestone.databinding.ItemMilestoneBinding
import com.bernovia.mylestone.ui.milestonesList.model.Milestone
import com.bernovia.mylestone.utils.PreferenceManager
import com.github.vipulasri.timelineview.TimelineView



class MilestoneListAdapter : RecyclerView.Adapter<MilestoneListAdapter.ViewHolder>() {
    private lateinit var milestonesList: ArrayList<Milestone>
    private lateinit var context:Context
    private lateinit var deleteClickListner:DeleteClickListner

    private var preferenceManager:PreferenceManager= PreferenceManager.instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context
        val binding: ItemMilestoneBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_milestone, parent, false)
        return ViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(milestonesList[position])
        val mColors = arrayOf("#9f84c4", "#02a2dd", "#58b25e", "#eab715")

        holder.itemBinding.headerConstraintLayout.setBackgroundColor(Color.parseColor(mColors[position % 4]))
        if (preferenceManager.userId== milestonesList[position].user_id){
            holder.itemBinding.deleteImageView.visibility= View.VISIBLE
            holder.itemBinding.deleteImageView.setOnClickListener{v->deleteClickListner.deleteOnClick(v, milestonesList[position].id,position)}
        }else {
            holder.itemBinding.deleteImageView.visibility= View.GONE

        }



    }

    override fun getItemCount(): Int {
        return if (::milestonesList.isInitialized) milestonesList.size else 0
    }

    fun updatePostList(milestoneList: ArrayList<Milestone>,deleteClickListner: DeleteClickListner) {
        this.deleteClickListner = deleteClickListner
        this.milestonesList = milestoneList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMilestoneBinding, viewType: Int) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = MilestoneViewModel()
        val  itemBinding: ItemMilestoneBinding=binding

        fun bind(milestone: Milestone) {
            viewModel.bind(milestone)
            binding.viewModel = viewModel
        }

        init {

            binding.timeline.initLine(viewType)
        }
    }


    fun removeItem(position: Int) {
        milestonesList.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }


    interface DeleteClickListner {

        fun deleteOnClick(v: View,id: Int,position: Int)


    }

}