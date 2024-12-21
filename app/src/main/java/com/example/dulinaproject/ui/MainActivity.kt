package com.example.dulinaproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dulinaproject.R
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.data.db.JokeDatabase
import com.example.dulinaproject.databinding.MainActivityBinding
import com.example.dulinaproject.ui.jokeDetails.JokeDetailsFragment
import com.example.dulinaproject.ui.jokeList.JokeListFragment
import com.example.dulinaproject.ui.utils.OnJokeClickListener

class MainActivity : AppCompatActivity(), OnJokeClickListener {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        JokeDatabase.initDatabase(this)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            openFragment()
        }
    }

    private fun openFragment() {
        val fragment = JokeListFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    override fun onJokeClick(joke: Joke) {
        openJokeDetailedFragment(joke)
    }

    private fun openJokeDetailedFragment(joke: Joke) {
        val jokeDetailsFragment = JokeDetailsFragment.newInstance(joke)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, jokeDetailsFragment)
            .addToBackStack(null)
            .commit()
    }
}