package com.example.dulinaproject.ui.jokeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.databinding.FragmentJokeDetailsBinding
import kotlinx.coroutines.launch
//
class JokeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentJokeDetailsBinding
    private lateinit var jokeDetailsViewModel: JokeDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJokeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        handleExtra()
    }

    private fun initViewModel() {
        jokeDetailsViewModel =
            ViewModelProvider(requireActivity())[JokeDetailsViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                jokeDetailsViewModel.joke.collect { joke ->
                    showJokeInfo(joke)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                jokeDetailsViewModel.error.collect { error ->
                    if (error.isNotEmpty())
                        errorCloseScreen(error)
                }
            }
        }
    }

    private fun handleExtra() {
        val joke =
            arguments?.getSerializable(JOKE_EXTRA)
        jokeDetailsViewModel.loadJokeDetailsData(joke)
    }

    private fun showJokeInfo(joke: Joke) {
        with(binding) {
            jokeDetailsPreciseCategory.text = joke.category
            jokeDetailsPreciseQuestion.text = joke.question
            jokeDetailsPreciseAnswer.text = joke.answer
        }
    }

    private fun errorCloseScreen(errorMessage: String) {
        Toast.makeText(requireActivity().baseContext, errorMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val JOKE_EXTRA = "SOME_JOKE"

        fun newInstance(joke: Joke): JokeDetailsFragment {
            return JokeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(JOKE_EXTRA, joke)
                }
            }
        }
    }
}