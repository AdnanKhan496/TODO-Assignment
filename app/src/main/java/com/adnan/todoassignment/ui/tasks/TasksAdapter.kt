package com.adnan.todoassignment.ui.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adnan.todoassignment.data.SubTask
import com.adnan.todoassignment.data.Task
import com.adnan.todoassignment.databinding.ItemParentListBinding
import com.adnan.todoassignment.databinding.ItemTaskBinding
import com.adnan.todoassignment.ui.subtasks.SubTasksAdapter

class TasksAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Task, TasksAdapter.TasksViewHolder>(DiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding =
            ItemParentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class TasksViewHolder(private val binding: ItemParentListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
                /* checkBoxCompleted.setOnClickListener {
                     val position = adapterPosition
                     if (position != RecyclerView.NO_POSITION) {
                         val task = getItem(position)
                         listener.onCheckBoxClick(task, checkBoxCompleted.isChecked)
                     }
                 }*/
            }
        }

        fun bind(task: Task) {
            binding.apply {
                //  checkBoxCompleted.isChecked = task.completed
                textViewName.text = task.name
                addChildTask.setOnClickListener(View.OnClickListener {
                    listener.onSubTaskAddButtonClick(task)
                })
                // textViewPrice.text = "RS/- "+ task.price
                textViewName.paint.isStrikeThruText = task.completed
                //  textViewPrice.paint.isStrikeThruText = task.completed
                //  labelPriority.isVisible = task.important
                rvSubTasks.apply {
                    adapter = SubTasksAdapter()
                    layoutManager = LinearLayoutManager(rvSubTasks.context)
                    setHasFixedSize(true)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(task: Task)
        fun onCheckBoxClick(task: Task, isChecked: Boolean)
        fun onSubTaskAddButtonClick(task: Task)
    }

    class DiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }
}