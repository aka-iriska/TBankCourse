package com.example.dulinaproject.ui.jokeDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.databinding.ActivityJokeDetailsBinding

class JokeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokeDetailsBinding

    private lateinit var jokeDetailsListViewModel: JokeDetailsViewModel

    // правило открытия activity
    companion object {
        private const val JOKE_POSITION_EXTRA_DEFAULT_VALUE: Int = -1
        private const val JOKE_POSITION_EXTRA = "JOKE_POSITION"

        fun getInstance(context: Context, jokePosition: Int): Intent {
            return Intent(context, JokeDetailsActivity::class.java).apply {
                putExtra(JOKE_POSITION_EXTRA, jokePosition)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) { // в bundle должна содержаться extra?
        super.onCreate(savedInstanceState)
        binding = ActivityJokeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        handleExtra()
    }

    private fun initViewModel() {
        val factory = JokeDetailsViewModelFactory()
        jokeDetailsListViewModel =
            ViewModelProvider(this, factory)[JokeDetailsViewModel::class.java]

        jokeDetailsListViewModel.joke.observe(this) {
            showJokeInfo(it)
        }
        jokeDetailsListViewModel.error.observe(this) {
            errorCloseScreen(it)
        }
    }

    private fun handleExtra() {
        val jokePosition =
            intent.getIntExtra(JOKE_POSITION_EXTRA, JOKE_POSITION_EXTRA_DEFAULT_VALUE)
        jokeDetailsListViewModel.loadJokeDetailsData(jokePosition)
    }

    private fun showJokeInfo(joke: Joke) {
        with(binding) {
            jokeDetailsPreciseCategory.text = joke.category
            jokeDetailsPreciseQuestion.text = joke.question
            jokeDetailsPreciseAnswer.text = joke.answer
        }
    }

    private fun errorCloseScreen(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        finish()
    }

}