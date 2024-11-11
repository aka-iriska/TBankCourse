package com.example.dulinaproject.ui.jokeList

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dulinaproject.databinding.ActivityMainBinding
import com.example.dulinaproject.ui.jokeDetails.JokeDetailsActivity
import com.example.dulinaproject.ui.jokeList.recycler.adapter.JokeListAdapter
import com.example.dulinaproject.ui.jokeList.recycler.util.JokeItemDiffCallback


class JokeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var jokeListViewModel: JokeListViewModel

    private val adapter by lazy {
        JokeListAdapter(JokeItemDiffCallback()) {
            startActivity(JokeDetailsActivity.getInstance(this, it))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initViewModel()
        jokeListViewModel.getJokesList()
    }

    private fun initRecyclerView() {
        binding.jokesRecyclerView.adapter = adapter
        binding.jokesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initViewModel() {
        val factory = JokeViewModelFactory()
        jokeListViewModel = ViewModelProvider(this, factory)[JokeListViewModel::class.java]

        jokeListViewModel.jokes.observe(this) {
            adapter.submitList(it)
            // adapter.setNewData(it) ???
        }
        jokeListViewModel.error.observe(this) {
            showError(it)
        }
    }

    private fun showError(errorMessage: String?) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }
}