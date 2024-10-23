package com.example.dulinaproject.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.databinding.JokeItemBinding

class JokeViewHolder(
    private val binding: JokeItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    // каждый viewHolder держит ссылку на конкретную view списка
    fun bind(joke: Joke) {
        binding.jokeCategory.text = joke.category
        binding.jokeQuestion.text = joke.question
        binding.jokeAnswer.text = joke.answer
    }
}