package com.example.dulinaproject.presentation.ui.jokeList.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dulinaproject.databinding.JokeItemBinding
import com.example.dulinaproject.domain.entity.Joke
import com.example.dulinaproject.presentation.ui.jokeList.recycler.JokeViewHolder
import com.example.dulinaproject.presentation.ui.jokeList.recycler.util.JokeItemDiffCallback

class JokeListAdapter(
    itemCallback: JokeItemDiffCallback,
    private val clickListener: (Joke) -> Unit
) : ListAdapter<Joke, JokeViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater, parent, false)

        return JokeViewHolder(binding).apply {
            binding.root.setOnClickListener {
                handleJokeClick(adapterPosition)
            }
        }

    }

    override fun getItemCount(): Int = currentList.size

    // вызывается при скроллинге
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun handleJokeClick(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            currentList[position]?.let {
                clickListener(currentList[position])
            }
        }
    }
}