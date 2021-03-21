package com.adnan.todoassignment.ui.alltaskslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adnan.todoassignment.R
import com.adnan.todoassignment.data.Task
import com.adnan.todoassignment.databinding.ItemTaskBinding

import com.adnan.todoassignment.ui.alltaskslist.AllTasksAdapter.AllTasksViewHolder

class AllTasksAdapter() :
    ListAdapter<Task, AllTasksViewHolder>(AllTasksAdapter.DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTasksViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllTasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllTasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class AllTasksViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        //listener.onItemClick(task)
                    }
                }
                checkBoxCompleted.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                       // listener.onCheckBoxClick(task, checkBoxCompleted.isChecked)
                    }
                }
            }
        }

        fun bind(task: Task) {
            binding.apply {
                checkBoxCompleted.isChecked = task.completed
                checkBoxCompleted.visibility = View.INVISIBLE
                llSide.backgroundTintList = ContextCompat.getColorStateList(llSide.context, R.color.black)
                textViewName.text = task.name
                textViewPrice.text = "RS/- "+ task.price
                textViewName.paint.isStrikeThruText = task.completed
                textViewPrice.paint.isStrikeThruText = task.completed
                labelPriority.isVisible = task.important
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }


}