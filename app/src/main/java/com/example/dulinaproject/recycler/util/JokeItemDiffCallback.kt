package com.example.dulinaproject.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.example.dulinaproject.data.Joke

class JokeItemDiffCallback : DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.question == newItem.question
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }
}