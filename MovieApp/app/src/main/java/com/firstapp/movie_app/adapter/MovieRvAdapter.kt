package com.firstapp.movie_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firstapp.movie_app.databinding.MovieContainerBinding
import com.firstapp.movie_app.model.Movies

class MovieRvAdapter : RecyclerView.Adapter<MovieRvAdapter.ListViewHolder>() {

    private val rvMovieList = ArrayList<Movies>()

    fun setMovieList(list : ArrayList<Movies>){
        rvMovieList.clear()
        rvMovieList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(private val binding: MovieContainerBinding): RecyclerView.ViewHolder(binding.root){
        fun bind (movies: Movies){
            binding.apply {
                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/w500/"+movies.poster_path)
                    .fitCenter()
                    .into(ivMoviePoster)
                tvMovieTitle.text = movies.title
                tvOverview.text = movies.overview
                tvReleaseDate.text = movies.release_date.substring(0,4)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = MovieContainerBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(rvMovieList[position])
    }

    override fun getItemCount(): Int = rvMovieList.size
}