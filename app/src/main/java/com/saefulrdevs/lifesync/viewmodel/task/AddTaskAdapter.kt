package com.saefulrdevs.lifesync.viewmodel.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.saefulrdevs.lifesync.R
import com.saefulrdevs.lifesync.data.model.TaskGroup

class AddTaskAdapter : ListAdapter<TaskGroup, AddTaskAdapter.TaskGroupViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskGroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_task_group, parent, false)
        return TaskGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskGroupViewHolder, position: Int) {
        val taskGroup = getItem(position)
        holder.bind(taskGroup)
    }

    class TaskGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskGroupTitle: TextView = itemView.findViewById(R.id.taskGroupTitle)

        fun bind(taskGroup: TaskGroup) {
            taskGroupTitle.text = taskGroup.title
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TaskGroup>() {
        override fun areItemsTheSame(oldItem: TaskGroup, newItem: TaskGroup): Boolean {
            return oldItem.idGroup == newItem.idGroup
        }

        override fun areContentsTheSame(oldItem: TaskGroup, newItem: TaskGroup): Boolean {
            return oldItem == newItem
        }
    }
}
