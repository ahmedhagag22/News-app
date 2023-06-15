package com.example.new_app.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.new_app.api.model.sourcesResponse.Source

@Dao
interface SourcesDao {
    @Query("Select *From Source")
    suspend fun getSources(): List<Source>

    @Query("Select *From Source where category= :category")
    suspend fun getSourcesByCategoryId(category: String): List<Source>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSources(source: List<Source>)
}