package com.example.dulinaproject.presentation.ui.jokeList

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
import com.example.dulinaproject.presentation.ui.jokeCreation.JokeCreationFragment
import com.example.dulinaproject.presentation.ui.jokeList.recycler.adapter.JokeListAdapter
import com.example.dulinaproject.presentation.ui.jokeList.recycler.util.JokeItemDiffCallback
import com.example.dulinaproject.presentation.ui.utils.OnJokeClickListener
import kotlinx.coroutines.launch

class JokeListFragment : Fragment() {

    private lateinit var binding: FragmentJokeListBinding
    private lateinit var jokeListViewModel: JokeListViewModel
    private lateinit var clickListener: OnJokeClickListener  // Слушатель нажатий на шутки
    private lateinit var paginationScrollListener: PaginationScrollListener

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
        initViewModel()
        loadJokes()
        initRecyclerView()

        binding.createButton.setOnClickListener {
            openJokeCreationFragment()
        }
    }

    private fun initRecyclerView() {
        binding.jokesRecyclerView.visibility = View.GONE
        binding.jokesRecyclerView.adapter = adapter
        binding.jokesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        paginationScrollListener = PaginationScrollListener(
            loadJokes = ::paginationLoadJokes
        )
        binding.jokesRecyclerView.addOnScrollListener(paginationScrollListener)
    }

    private fun initViewModel() {
        jokeListViewModel = ViewModelProvider(requireActivity())[JokeListViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Загрузка
                jokeListViewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        binding.shimmerLayout.startShimmer()
                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.jokesRecyclerView.visibility = View.GONE
                        binding.emptyListMessage.visibility = View.GONE
                    } else {
                        binding.shimmerLayout.visibility = View.GONE
                        binding.shimmerLayout.stopShimmer()

                        val jokesList = jokeListViewModel.jokes.value
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
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                jokeListViewModel.jokes.collect { jokesList ->
                    if (jokesList.isNotEmpty() && !jokeListViewModel.isLoading.value) {
                        binding.emptyListMessage.visibility = View.GONE
                        binding.jokesRecyclerView.visibility = View.VISIBLE
                        adapter.submitList(jokesList)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Ошибки
                jokeListViewModel.error.collect { errorMessage ->
                    if (errorMessage.isNotEmpty()) {
                        showError(errorMessage)
                    }
                }
            }
        }
    }


    private fun loadJokes() {
            jokeListViewModel.loadJokes()
    }

    private fun paginationLoadJokes() {
        jokeListViewModel.paginationLoadJokes() {
            paginationScrollListener.finishPaginationLoading()
        }
    }

    private fun showError(errorMessage: String?) {
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