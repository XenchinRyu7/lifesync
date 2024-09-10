package com.saefulrdevs.lifesync.viewmodel.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.data.model.TaskWithGroup
import com.saefulrdevs.lifesync.data.repository.TaskRepository
import com.saefulrdevs.lifesync.databinding.CardInProgressBinding

class CardInProgressAdapter : RecyclerView.Adapter<CardInProgressAdapter.CardViewHolder>() {

    private var cardList = listOf<TaskWithGroup>()

    class CardViewHolder(val binding: CardInProgressBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            CardInProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardList[position]

        holder.binding.apply {
            titleTaskGroup.text = currentItem.taskGroup?.title ?: "No Group"
            titleTask.text = currentItem.task.title

            currentItem.taskGroup?.icon?.let {
                iconGroup.setImageResource(it)
            }

            progressTask.progress = currentItem.task.progress
        }
    }

    override fun getItemCount() = cardList.size

    fun setCards(cards: List<TaskWithGroup>) {
        this.cardList = cards
        notifyDataSetChanged()
    }
}
