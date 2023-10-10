package com.firstapp.movie_app.viewmodel

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firstapp.movie_app.MainActivity
import com.firstapp.movie_app.api.ApiConfig
import com.firstapp.movie_app.model.MovieResponse
import com.firstapp.movie_app.model.Movies
import com.firstapp.movie_app.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(application: Application) : ViewModel() {

    private val movieRepo : MovieRepository = MovieRepository(application)

    fun loadMovies(page: Int){
        ApiConfig.getApiService()
            .getMovies("f7b67d9afdb3c971d4419fa4cb667fbf",page)
            .enqueue(object : Callback<MovieResponse>{
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    movieRepo.insertToDB(response.body()!!.results)
                    loadMore(page)
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    t.message?.let { Log.d("Movie",it) }
                }

            })
    }

    fun loadMore(page: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            clearDB()
            loadMovies(page+1)
        }, 60000)
    }

    private fun clearDB(){
        CoroutineScope(Dispatchers.IO).launch {
            movieRepo.deleteAll()
        }
    }

    fun getMovieList() : LiveData<List<Movies>> {
        return movieRepo.getAll()
    }
}