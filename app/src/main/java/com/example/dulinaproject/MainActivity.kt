package com.example.dulinaproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.databinding.ActivityMainBinding
import com.example.dulinaproject.recycler.adapter.JokeListAdapter
import com.example.dulinaproject.recycler.util.JokeItemCallback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val itemCallback = JokeItemCallback()
    private val adapter by lazy { JokeListAdapter(itemCallback) }

    private val data = listOf(
        Joke(
            category = "Программирование",
            question = "Почему программисты не любят природу?",
            answer = "Слишком много багов в лесу!"
        ),
        Joke(
            category = "Программирование",
            question = "Почему программистам сложно находить пару?",
            answer = "Потому что они постоянно ищут переменную, а не постоянную!"
        ),
        Joke(
            category = "Программирование",
            question = "Какой любимый напиток у кодеров?",
            answer = "Смузи!"
        ),
        Joke(
            category = "Общество",
            question = "Почему никто не берет в долг у математиков?",
            answer = "Потому что они всегда оставляют проценты!"
        ),
        Joke(
            category = "Общество",
            question = "Как называют человека, который продал свою печень?",
            answer = "Обеспеченный"
        ),
        Joke(
            "Мультфильмы",
            "Почему черепашки ниндзя нападают вчетвером?",
            "У них учитель крыса"
        ),
        Joke(
            "Природа",
            "Что будет, если ворона сядет на оголённые провода?",
            "Электро Кар"
        ),
        Joke(
            "Общество",
            "Где лежат книги о дискриминации карликов?",
            "На верхней полке"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter.submitList(data)
        binding.jokesRecyclerView.adapter = adapter
        binding.jokesRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}