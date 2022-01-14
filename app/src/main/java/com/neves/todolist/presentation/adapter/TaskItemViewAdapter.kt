package com.neves.todolist.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.neves.domain.model.Task
import com.neves.todolist.R
import com.neves.todolist.databinding.ItemTaskBinding
import com.neves.todolist.presentation.state.TaskItemUiState

class TaskItemViewAdapter:
    ListAdapter<TaskItemUiState, TaskItemViewAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TaskItemUiState) {
            binding.tituloTask.text = item.titulo
            setIconCheck(item.done)
            binding.iconCheckTaskItem.setOnClickListener {
                item.onTaskCheck()
            }
        }

        private fun setIconCheck(done:Boolean){
            if(done) binding.iconCheckTaskItem.setImageResource(R.drawable.ic_baseline_check_circle_24);
            else binding.iconCheckTaskItem.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
        }
    }

}

class DiffCallback : DiffUtil.ItemCallback<TaskItemUiState>() {
    override fun areItemsTheSame(oldItem: TaskItemUiState, newItem: TaskItemUiState) = oldItem == newItem
    override fun areContentsTheSame(oldItem: TaskItemUiState, newItem: TaskItemUiState) =
        oldItem.id == newItem.id
}