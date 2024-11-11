package com.example.dulinaproject.ui.jokeList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dulinaproject.databinding.FragmentJokeListBinding
import com.example.dulinaproject.ui.OnJokeClickListener
import com.example.dulinaproject.ui.jokeList.recycler.adapter.JokeListAdapter
import com.example.dulinaproject.ui.jokeList.recycler.util.JokeItemDiffCallback

class JokeListFragment : Fragment() {

    private lateinit var binding: FragmentJokeListBinding
    private lateinit var jokeListViewModel: JokeListViewModel
    private val context = requireActivity().baseContext
    private lateinit var clickListener: OnJokeClickListener  // Слушатель нажатий на шутки

    private val adapter by lazy {
        JokeListAdapter(JokeItemDiffCallback()) {
            clickListener.onJokeClick(it)
        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJokeListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.let {
            initRecyclerView()
            initViewModel()
            jokeListViewModel.getJokesList()
        }
    }

    private fun initRecyclerView() {
        binding.jokesRecyclerView.adapter = adapter
        binding.jokesRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initViewModel() {
        val factory = JokeViewModelFactory()
        jokeListViewModel = ViewModelProvider(this, factory)[JokeListViewModel::class.java]

        jokeListViewModel.jokes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        jokeListViewModel.error.observe(viewLifecycleOwner) {
            showError(it)
        }
    }

    private fun showError(errorMessage: String?) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = JokeListFragment()
    }
}