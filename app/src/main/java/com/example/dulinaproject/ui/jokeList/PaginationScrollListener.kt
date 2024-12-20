package com.example.dulinaproject.ui.jokeList

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
    private val loadJokes: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        println(totalItemCount)
        println(lastVisibleItemPosition)
        if (totalItemCount == lastVisibleItemPosition + 1) { // порог 5 элементов
            println("check")
            loadJokes()
        }
    }
}