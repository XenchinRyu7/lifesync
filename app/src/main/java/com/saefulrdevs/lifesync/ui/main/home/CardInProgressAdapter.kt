package com.saefulrdevs.lifesync.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saefulrdevs.lifesync.data.model.Task
import com.saefulrdevs.lifesync.databinding.CardInProgressBinding

class CardInProgressAdapter : RecyclerView.Adapter<CardInProgressAdapter.CardViewHolder>() {

    private var cardList = listOf<Task>()

    // ViewHolder harus menerima binding object dari onCreateViewHolder
    class CardViewHolder(val binding: CardInProgressBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding =
            CardInProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardList[position]

        holder.binding.apply {
            titleTaskGroup.text = currentItem.group
            titleTask.text = currentItem.title
            iconGroup.setImageResource(currentItem.iconGroup)
            progressTask.progress = currentItem.progress
        }
    }

    override fun getItemCount() = cardList.size

    fun setCards(cards: List<Task>) {
        this.cardList = cards
        notifyDataSetChanged()
    }
}