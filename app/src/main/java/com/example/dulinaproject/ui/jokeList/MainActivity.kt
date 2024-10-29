package com.example.dulinaproject.ui.jokeList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dulinaproject.data.JokeData
import com.example.dulinaproject.databinding.ActivityMainBinding
import com.example.dulinaproject.ui.jokeDetails.JokeDetailsActivity
import com.example.dulinaproject.ui.jokeList.recycler.adapter.JokeListAdapter
import com.example.dulinaproject.ui.jokeList.recycler.util.JokeItemDiffCallback


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy {
        JokeListAdapter(JokeItemDiffCallback()) {
            startActivity(JokeDetailsActivity.getInstance(this, it))
        }
    }

    private val data = JokeData.data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter.submitList(data)
        binding.jokesRecyclerView.adapter = adapter
        binding.jokesRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}