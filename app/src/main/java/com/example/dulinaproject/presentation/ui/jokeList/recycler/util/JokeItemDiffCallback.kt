package com.example.dulinaproject.presentation.ui.jokeList.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.example.dulinaproject.domain.entity.Joke

class JokeItemDiffCallback : DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.question == newItem.question
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }
}