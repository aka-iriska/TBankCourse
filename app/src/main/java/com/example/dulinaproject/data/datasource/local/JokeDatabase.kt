package com.example.dulinaproject.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dulinaproject.data.entity.CachedJoke
import com.example.dulinaproject.data.entity.UserJoke

@Database(entities = [UserJoke::class, CachedJoke::class], version = 1, exportSchema = false)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao

    companion object {
        @Volatile
        lateinit var INSTANCE: JokeDatabase

        fun initDatabase(context: Context) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                JokeDatabase::class.java,
                "joke_database"
            ).build()
            INSTANCE = instance
        }
    }
}