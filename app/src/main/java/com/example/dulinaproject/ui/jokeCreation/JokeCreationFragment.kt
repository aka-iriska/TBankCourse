package com.example.dulinaproject.ui.jokeCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.databinding.FragmentCreateJokeBinding
import java.util.UUID

class JokeCreationFragment : Fragment() {
    private lateinit var binding: FragmentCreateJokeBinding
    private lateinit var newJokeViewModel: JokeCreationViewModel

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
        val factory = JokeCreationViewModelFactory()

        newJokeViewModel =
            ViewModelProvider(this, factory)[JokeCreationViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {
        newJokeViewModel.savedSuccessfully.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Шутка добавлена!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            }
        }

        newJokeViewModel.error.observe(viewLifecycleOwner) {
            showError(it)
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
        newJokeViewModel.saveJoke(newJoke)
    }

    companion object {

        fun newInstance(): JokeCreationFragment {
            return JokeCreationFragment()
        }
    }
}