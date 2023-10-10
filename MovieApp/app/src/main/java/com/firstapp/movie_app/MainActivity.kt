package com.firstapp.movie_app

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.firstapp.movie_app.adapter.MovieRvAdapter
import com.firstapp.movie_app.databinding.ActivityMainBinding
import com.firstapp.movie_app.model.Movies
import com.firstapp.movie_app.viewmodel.MovieViewModel
import com.firstapp.movie_app.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    companion object {
        const val ACTION_MORE_MOVIES = "action_more_movies"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvMovieRvAdapter: MovieRvAdapter
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var loadMoreMoviesReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val factory = ViewModelFactory.getInstance(application)
            movieViewModel = ViewModelProvider(this@MainActivity,factory)[MovieViewModel::class.java]

            rvMovieRvAdapter = MovieRvAdapter()
            rvMovieRvAdapter.notifyDataSetChanged()

            rvMovie.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMovie.adapter = rvMovieRvAdapter

            var page: Int = 1

            movieViewModel.loadMovies(page)

            loadMoreMoviesReceiver = object : BroadcastReceiver() {
                override fun onReceive(p0: Context?, p1: Intent?) {
                    notifWhenMovieExist()
                }
            }

            val moreMoviesIntentFilter = IntentFilter(ACTION_MORE_MOVIES)
            registerReceiver(loadMoreMoviesReceiver, moreMoviesIntentFilter)

            movieViewModel.getMovieList().observe(this@MainActivity){
                onContentChanged().apply {
                    val notifyFinishIntent = Intent().setAction(ACTION_MORE_MOVIES)
                    sendBroadcast(notifyFinishIntent)
                }

                val arrayListMovie = bindList(it)

                loadMovies.setOnClickListener {
                    popUp.visibility = View.GONE
                    rvMovieRvAdapter.setMovieList(arrayListMovie)
                }
            }
        }
    }

    private fun notifWhenMovieExist(){
        var message = "New Movie Available!"
        binding.apply {
            popUp.visibility = View.VISIBLE
            tvMsg.text = message
        }
    }

    private fun bindList (listMovie : List<Movies>) : ArrayList<Movies>{
        val movieList = ArrayList<Movies>()
        for (movie in listMovie){
            val movieBind = Movies (
                movie.movieId,
                movie.id,
                movie.title,
                movie.release_date,
                movie.poster_path,
                movie.overview,
            )
            movieList.add(movieBind)
        }
        return movieList
    }

    private fun notifWhenNotExist(){
        var message = "No Movie Available"
        binding.apply {
            popUp.visibility = View.VISIBLE
            loadMovies.visibility = View.GONE
            tvMsg.text = message
        }
    }
}