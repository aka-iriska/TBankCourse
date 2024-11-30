package com.example.dulinaproject.ui.jokeList

import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dulinaproject.R
import com.example.dulinaproject.databinding.FragmentJokeListBinding
import com.example.dulinaproject.ui.jokeCreation.JokeCreationFragment
import com.example.dulinaproject.ui.jokeList.recycler.adapter.JokeListAdapter
import com.example.dulinaproject.ui.jokeList.recycler.util.JokeItemDiffCallback
import com.example.dulinaproject.ui.utils.OnJokeClickListener
import kotlinx.coroutines.launch

class JokeListFragment : Fragment() {

    private lateinit var binding: FragmentJokeListBinding
    private lateinit var jokeListViewModel: JokeListViewModel
    private lateinit var clickListener: OnJokeClickListener  // Слушатель нажатий на шутки

    private val adapter by lazy {
        JokeListAdapter(JokeItemDiffCallback()) {
            clickListener.onJokeClick(it)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnJokeClickListener) {
            clickListener = context
        } else {
            throw RuntimeException("Activity must implement OnJokeClickListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJokeListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
        loadJokes()

        binding.createButton.setOnClickListener {
            openJokeCreationFragment()
        }
    }

    private fun initRecyclerView() {
        binding.jokesRecyclerView.adapter = adapter
        binding.jokesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViewModel() {
        jokeListViewModel = ViewModelProvider(requireActivity())[JokeListViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                jokeListViewModel.jokes.collect { jokesList ->
                    binding.progressBar.visibility = View.GONE
                    if (jokesList.isEmpty()) {
                        binding.emptyListMessage.visibility = View.VISIBLE
                        binding.jokesRecyclerView.visibility = View.GONE
                    } else {
                        binding.emptyListMessage.visibility = View.GONE
                        binding.jokesRecyclerView.visibility = View.VISIBLE
                        adapter.submitList(jokesList)
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                jokeListViewModel.error.collect {
                    showError(it)
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                jokeListViewModel.isLoading.collect { isLoading ->
                    if (isLoading){
                        binding.progressBar.visibility = View.VISIBLE
                        binding.emptyListMessage.visibility = View.GONE
                        binding.jokesRecyclerView.visibility = View.GONE
                    }
                    else
                        binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun loadJokes() {

        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                jokeListViewModel.getJokesList()
            } catch (e: Exception) {
                showError("Ошибка загрузки данных") // вопрос правильно ли обходить viewModel и напрямую вызывать отсюда ошибку (не проводя всё через viewModel)
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun showError(errorMessage: String?) {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun openJokeCreationFragment() {
        val jokeCreationFragment = JokeCreationFragment.newInstance()

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, jokeCreationFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance(): JokeListFragment {
            return JokeListFragment()
        }
    }
}