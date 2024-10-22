package com.example.dulinaproject.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dulinaproject.data.Joke
import com.example.dulinaproject.databinding.JokeItemBinding

class JokeAdapter:RecyclerView.Adapter<JokeViewHolder>() { // общается с recycler view

    // статичный массив шуток
    private val data = listOf<Joke>(
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

    // вызываются для каждого viewHolder по одному разу
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // parent и false для отображения по всей ширине ?
        val binding = JokeItemBinding.inflate(inflater, parent, false)

        return JokeViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    // вызывается при скроллинге
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(data[position])
    }
}