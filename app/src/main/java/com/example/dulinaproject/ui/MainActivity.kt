package com.example.dulinaproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dulinaproject.R
import com.example.dulinaproject.ui.jokeDetails.JokeDetailsFragment
import com.example.dulinaproject.ui.jokeList.JokeListFragment

interface OnJokeClickListener {
    fun onJokeClick(jokePosition: Int)
}

class MainActivity : AppCompatActivity(), OnJokeClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            openFragment()
        }
    }

    private fun openFragment() {
        val fragment = JokeListFragment.newInstance(this)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }

    override fun onJokeClick(jokePosition: Int) {
        openJokeDetailedFragment(jokePosition)
    }

    private fun openJokeDetailedFragment(jokePosition: Int){
        val jokeDetailsFragment = JokeDetailsFragment.newInstance(jokePosition)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, jokeDetailsFragment)
            .addToBackStack(null)
            .commit()
    }
}