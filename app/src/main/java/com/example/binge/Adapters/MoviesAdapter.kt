package com.example.binge.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.binge.Models.MovieModel
import com.example.binge.R

class MoviesAdapter(
    private var movieModels: MutableList<MovieModel>,
    private val onMovieClick: (movieModel: MovieModel) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movieModels.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieModels[position])
    }

    fun appendMovies(movieModels: List<MovieModel>) {
        this.movieModels.addAll(movieModels)
        notifyItemRangeInserted(
            this.movieModels.size,
            movieModels.size - 1
        )
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)

        fun bind(movieModel: MovieModel) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movieModel.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            itemView.setOnClickListener { onMovieClick.invoke(movieModel) }
        }
    }
}