package com.example.dulinaproject.presentation.ui.jokeList.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.dulinaproject.domain.entity.Joke
import com.example.dulinaproject.databinding.JokeItemBinding

class JokeViewHolder(
    private val binding: JokeItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    // каждый viewHolder держит ссылку на конкретную view списка
    fun bind(joke: Joke) {
        if (!joke.isFromApi) {
            binding.jokeCategory.setTextColor(0x757575FF)
            binding.jokeQuestion.setTextColor(0x757575FF)
            binding.jokeAnswer.setTextColor(0x757575FF)
        }
        binding.jokeCategory.text = joke.category
        binding.jokeQuestion.text = joke.question
        binding.jokeAnswer.text = joke.answer
    }
}