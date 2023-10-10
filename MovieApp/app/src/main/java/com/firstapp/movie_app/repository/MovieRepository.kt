package com.firstapp.movie_app.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.firstapp.movie_app.database.MovieDao
import com.firstapp.movie_app.database.MovieDatabase
import com.firstapp.movie_app.model.Movies
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MovieRepository (application: Application) {
    private val movieDao: MovieDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val database = MovieDatabase.getDatabase(application)
        movieDao = database.movieDao()
    }

    fun insertToDB (movies: ArrayList<Movies>) {
        executorService.execute { movieDao.insertMovie(movies) }
    }

    fun getAll() : LiveData<List<Movies>> = movieDao.getAllMovie()

    suspend fun countAllMovie() : Int = movieDao.countMovie()

    fun deleteAll() = movieDao.cleanDB()

}