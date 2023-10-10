package com.firstapp.movie_app.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.firstapp.movie_app.model.Movies

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movies: ArrayList<Movies>)

    @Query("Select * from movies limit 10")
    fun getAllMovie(): LiveData<List<Movies>>

    @Query("Delete from movies")
    fun cleanDB()
}