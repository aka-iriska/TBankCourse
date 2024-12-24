package com.example.dulinaproject.presentation.ui.jokeCreation

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
import com.example.dulinaproject.data.datasource.local.JokeDatabase
import com.example.dulinaproject.data.datasource.local.LocalDataSourceImpl
import com.example.dulinaproject.data.datasource.remote.JokeNetwork
import com.example.dulinaproject.data.mapper.JokeDbModelMapper
import com.example.dulinaproject.data.repository.JokeRepositoryImpl
import com.example.dulinaproject.domain.entity.Joke
import com.example.dulinaproject.databinding.FragmentCreateJokeBinding
import com.example.dulinaproject.domain.usecase.ClearOldCache
import com.example.dulinaproject.domain.usecase.GetApiJokes
import com.example.dulinaproject.domain.usecase.GetCachedJokes
import com.example.dulinaproject.domain.usecase.GetUserJokesUseCase
import com.example.dulinaproject.domain.usecase.SaveUserJokes
import com.example.dulinaproject.presentation.ui.jokeList.JokeListViewModel
import com.example.dulinaproject.presentation.ui.jokeList.JokeListViewModelFactory
import kotlinx.coroutines.launch
import java.util.UUID

class JokeCreationFragment : Fragment() {
    private lateinit var binding: FragmentCreateJokeBinding
    private lateinit var newJokeViewModel: JokeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.saveButton.setOnClickListener {
            saveJoke()
        }
    }

    private fun initViewModel() {
        val jokeRepository = JokeRepositoryImpl(
            localDataSource = LocalDataSourceImpl(dao = JokeDatabase.INSTANCE.jokeDao()),
            networkService = JokeNetwork(),
            jokeDbModelMapper = JokeDbModelMapper()
        )

        val factory = JokeListViewModelFactory(
            getApiJokes = GetApiJokes(jokeRepository),
            getUserJokesUseCase = GetUserJokesUseCase(jokeRepository),
            getCachedJokes = GetCachedJokes(jokeRepository),
            saveUserJokes = SaveUserJokes(jokeRepository),
            clearOldCache = ClearOldCache(jokeRepository)
        )
        newJokeViewModel =
            ViewModelProvider(requireActivity(), factory)[JokeListViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                newJokeViewModel.savedSuccessfully.collect { success ->
                    if (success) {
                        Toast.makeText(requireContext(), "Joke is added", Toast.LENGTH_SHORT)
                            .show()
                        parentFragmentManager.popBackStack()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                newJokeViewModel.error.collect { error ->
                    if (error.isNotEmpty())
                        showError(error)
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun saveJoke() {
        val newJoke = Joke(
            id = UUID.randomUUID(),
            category = binding.categoryInput.text.toString(),
            question = binding.questionInput.text.toString(),
            answer = binding.answerInput.text.toString()
        )
        lifecycleScope.launch {
            newJokeViewModel.saveJoke(newJoke)
        }
    }

    companion object {

        fun newInstance(): JokeCreationFragment {
            return JokeCreationFragment()
        }
    }
}