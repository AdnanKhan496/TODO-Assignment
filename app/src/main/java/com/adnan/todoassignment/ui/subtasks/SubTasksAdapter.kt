package com.adnan.todoassignment.ui.subtasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adnan.todoassignment.data.SubTask
import com.adnan.todoassignment.data.Task
import com.adnan.todoassignment.databinding.ItemParentListBinding
import com.adnan.todoassignment.databinding.ItemTaskBinding

class SubTasksAdapter() :
    ListAdapter<SubTask, SubTasksAdapter.SubTasksViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTasksViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubTasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubTasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SubTasksViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                       // listener.onItemClick(task)
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

        fun bind(subTask: SubTask) {
            binding.apply {
                checkBoxCompleted.isChecked = subTask.completed
                textViewName.text = subTask.name
                textViewPrice.text = "RS/- " + subTask.price
                textViewName.paint.isStrikeThruText = subTask.completed
                textViewPrice.paint.isStrikeThruText = subTask.completed
                labelPriority.isVisible = subTask.important
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(subTask: SubTask)
        fun onCheckBoxClick(subTask: SubTask, isChecked: Boolean)
        fun onSubTaskAddButtonClick(subTask: SubTask)
    }

    class DiffCallback : DiffUtil.ItemCallback<SubTask>() {
        override fun areItemsTheSame(oldItem: SubTask, newItem: SubTask) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SubTask, newItem: SubTask) =
            oldItem == newItem
    }
}