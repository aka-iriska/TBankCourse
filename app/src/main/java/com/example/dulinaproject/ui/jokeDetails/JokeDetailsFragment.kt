package com.example.dulinaproject.ui.jokeDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.databinding.FragmentJokeDetailsBinding

class JokeDetailsFragment : Fragment() {
    //
    private lateinit var binding: FragmentJokeDetailsBinding
    private lateinit var jokeDetailsListViewModel: JokeDetailsViewModel

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
        val factory = JokeDetailsViewModelFactory()

        jokeDetailsListViewModel =
            ViewModelProvider(this, factory)[JokeDetailsViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {
        jokeDetailsListViewModel.joke.observe(viewLifecycleOwner) {
            showJokeInfo(it)
        }
        jokeDetailsListViewModel.error.observe(viewLifecycleOwner) {
            errorCloseScreen(it)
        }
    }

    private fun handleExtra() {
        val jokePosition =
            arguments?.getInt(JOKE_POSITION_EXTRA) ?: JOKE_POSITION_EXTRA_DEFAULT_VALUE
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
        Toast.makeText(requireActivity().baseContext, errorMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val JOKE_POSITION_EXTRA_DEFAULT_VALUE = -1
        private const val JOKE_POSITION_EXTRA = "JOKE_POSITION"

        fun newInstance(jokePosition: Int): JokeDetailsFragment {
            return JokeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(JOKE_POSITION_EXTRA, jokePosition)
                }
            }
        }
    }
}