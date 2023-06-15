package com.example.new_app.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.new_app.api.model.sourcesResponse.Source

@Database(entities = [Source::class], version = 1)
abstract class NewsDatabase  :RoomDatabase(){
    abstract fun getNewsDao():SourcesDao

    companion object{
        var database :NewsDatabase?=null
        const val DATA_BASE_NAME="NewsDataBase"

        fun init(context: Context)
        {
            if(database==null)
            {
                database = Room.databaseBuilder(context.applicationContext,
                    NewsDatabase::class.java, DATA_BASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }

        }
        fun getInstance(): NewsDatabase?
        {
            return database
        }
    }
}