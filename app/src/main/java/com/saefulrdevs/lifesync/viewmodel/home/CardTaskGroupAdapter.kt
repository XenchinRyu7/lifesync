package com.saefulrdevs.lifesync.viewmodel.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saefulrdevs.lifesync.data.model.TaskGroup
import com.saefulrdevs.lifesync.databinding.CardGroupBinding

class CardTaskGroupAdapter : RecyclerView.Adapter<CardTaskGroupAdapter.CardViewHolder>() {

    private var cardList = listOf<TaskGroup>()

    class CardViewHolder(val binding: CardGroupBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardList[position]

        holder.binding.apply {
            titleTaskGroup.text = currentItem.title
            currentItem.icon?.let { iconGroup.setImageResource(it) }
            circularProgressIndicator.progress = 10
            progressGroup.text = "10"
        }
    }

    override fun getItemCount() = cardList.size

    fun setCards(cards: List<TaskGroup>) {
        this.cardList = cards
        notifyDataSetChanged()
    }
}

