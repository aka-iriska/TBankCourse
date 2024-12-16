package com.example.dulinaproject.ui.jokeList

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dx <= 0 ) return
        ( recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
    }
}