package com.saefulrdevs.lifesync.viewmodel.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saefulrdevs.lifesync.data.model.DateCard
import com.saefulrdevs.lifesync.databinding.CardDateBinding

class CardDateAdapter : RecyclerView.Adapter<CardDateAdapter.CardViewHolder>() {
    private var cardList = listOf<DateCard>()

    class CardViewHolder(val binding: CardDateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardList[position]

        holder.binding.apply {
            textMonth.text = currentItem.month
            textDate.text = currentItem.date
            textDay.text = currentItem.day
        }
    }

    override fun getItemCount() = cardList.size

    fun setCards(cards: List<DateCard>) {
        this.cardList = cards
        notifyDataSetChanged()
    }
}