package com.example.dulinaproject.ui.jokeList

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
    private val loadJokes: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var isCurrentlyLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        if (!isCurrentlyLoading && totalItemCount == lastVisibleItemPosition + 1) { // порог 5 элементов
            Log.d("fetch", totalItemCount.toString())
            Log.d("fetch",lastVisibleItemPosition.toString())
            println("check")
            isCurrentlyLoading = true
            loadJokes()
        }
    }
     fun finishPaginationLoading (){
         isCurrentlyLoading = false
     }
}