package com.example.binge.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.binge.*
import com.example.binge.Activities.*
import com.example.binge.Adapters.MoviesAdapter
import com.example.binge.Extras.MoviesRepo
import com.example.binge.Models.MovieModel
import com.squareup.picasso.Picasso

class BingeFragment: Fragment(), View.OnClickListener {

    private lateinit var snackText: TextView
    private lateinit var movieText: TextView
    private lateinit var poster: ImageView
    private var posterPath: String? = ""
    private val snacks: Array<String> = arrayOf(
        "How about some caramel popcorn to go with this one?",
        "How about some salted popcorn to go with this one?",
        "Oh! Peanut M&Ms would be great for this!",
        "Oh! Milk Chocolate M&Ms would be great for this!",
        "Oh! Crispy M&Ms would be great for this!",
        "Hope you have Nachos ready!",
        "Wonder if there's any leftover Pizza?",
        "Grab a packet of Skittles and get ready!",
        "Some Soft Pretzels would go nicely with this viewing",
        "Grab a packet of Gummy Bears and get ready!",
        "Grab a packet of Crisps and get ready!",
        "Grab some Oreos and enjoy the movie!",
        "Something tangy or sour would suffice for this one!",
        "Grab a soda and enjoy the show!")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_binge, container, false)
        snackText = v.findViewById(R.id.snack_recommendation) as TextView
        movieText = v.findViewById(R.id.movie_recommendation) as TextView
        poster = v.findViewById(R.id.movie_poster_binge) as ImageView
        posterPath = ""
        val btn: Button = v.findViewById(R.id.button_binge)
        btn.setOnClickListener(this)
        getRandomMovies()
        return v;
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_binge -> {
                snackText.setText(snacks[(0..(snacks.size - 1)).random()])
                getRandomMovies()
                posterPath = (MoviesRepo.randomMoviePoster())
                Picasso.get().load("https://image.tmdb.org/t/p/w342$posterPath").into(poster)
                movieText.text = (MoviesRepo.randomMovieTitle())
            }
        }
    }

    private fun getRandomMovies() {
        MoviesRepo.getRandomMovies(
            ::getRandomMovies,
            ::onError
        )
    }

    private fun showMovieDetails(movieModel: MovieModel) {
        val intent = Intent(this.context, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movieModel.backdropPath)
        intent.putExtra(MOVIE_POSTER, movieModel.posterPath)
        intent.putExtra(MOVIE_TITLE, movieModel.title)
        intent.putExtra(MOVIE_RATING, movieModel.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movieModel.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movieModel.overview)
        startActivity(intent)
    }

    private fun onError() {
        Toast.makeText(this.context, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}
