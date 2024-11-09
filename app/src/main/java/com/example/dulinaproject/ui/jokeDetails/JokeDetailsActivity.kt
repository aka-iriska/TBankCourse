package com.example.dulinaproject.ui.jokeDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.JokeData
import com.example.dulinaproject.databinding.ActivityJokeDetailsBinding

class JokeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJokeDetailsBinding

    private val data by lazy {JokeData.data}

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

        handleExtra()
    }

    private fun handleExtra() {
        val jokePosition = intent.getIntExtra(JOKE_POSITION_EXTRA, JOKE_POSITION_EXTRA_DEFAULT_VALUE)

        if (jokePosition == -1) {
            handleError()
        } else {
            setupJokeData(data[jokePosition])
        }
    }

    private fun setupJokeData(joke: Joke) {
        with(binding) {
            jokeDetailsPreciseCategory.text = joke.category
            jokeDetailsPreciseQuestion.text = joke.question
            jokeDetailsPreciseAnswer.text = joke.answer
        }
    }

    private fun handleError() {
        Toast.makeText(this, "Invalid data", Toast.LENGTH_LONG).show()
        finish()
    }
}