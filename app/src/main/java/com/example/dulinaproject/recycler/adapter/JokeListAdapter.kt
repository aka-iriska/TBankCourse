package com.example.dulinaproject.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.databinding.JokeItemBinding
import com.example.dulinaproject.recycler.JokeViewHolder
import com.example.dulinaproject.recycler.util.JokeItemCallback

class JokeListAdapter(
    itemCallback: JokeItemCallback
) : ListAdapter<Joke, JokeViewHolder>(itemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = JokeItemBinding.inflate(inflater, parent, false)

        return JokeViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    // вызывается при скроллинге
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}