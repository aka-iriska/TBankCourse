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


/*
* Реализуйте пагинацию данных: когда долистываем до последней шутки подгружаем еще 10 шуток из сети.
* */

/*
* Добавьте индикацию загрузки данных пока ожидается ответ от сервера: шиммеры/спиннер/текст/анимированую картинку
* */
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

/*        binding.jokesRecyclerView.addOnScrollListener{

        }*/
    }

    private fun initRecyclerView() {
        binding.jokesRecyclerView.adapter = adapter
        binding.jokesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        /*val paginationScrollListener = PaginationScrollListener {
            loadJokes(true) // передаем метод для подгрузки данных
        }
        binding.jokesRecyclerView.addOnScrollListener(paginationScrollListener)
    */}

    private fun initViewModel() {
        jokeListViewModel = ViewModelProvider(requireActivity())[JokeListViewModel::class.java]

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Загрузка
                jokeListViewModel.isLoading.collect { isLoading ->
                    binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                    if (isLoading) {
                        binding.emptyListMessage.visibility = View.GONE
                        binding.jokesRecyclerView.visibility = View.GONE
                    }
                    else {
                        val jokesList = jokeListViewModel.jokes.value
                        if (jokesList.isEmpty()){
                            binding.emptyListMessage.visibility = View.VISIBLE
                            binding.jokesRecyclerView.visibility = View.GONE
                        }
                        else {
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
                // Ошибки
                jokeListViewModel.error.collect { errorMessage ->
                    if (errorMessage.isNotEmpty()) {
                        showError(errorMessage)
                        binding.emptyListMessage.visibility = View.GONE
                        binding.jokesRecyclerView.visibility = View.GONE
                    }
                }
            }
        }
    }


    private fun loadJokes(flag: Boolean = false) {
        jokeListViewModel.loadJokes(flag)
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