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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.binge.*
import com.example.binge.Activities.*
import com.example.binge.Extras.MoviesRepo
import com.example.binge.Models.MovieModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BingeFragment: Fragment(), View.OnClickListener {

    private var generating: Boolean = false
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
        "Grab a soda and enjoy the show!",
        "A bunch of cookies would go well with this",
        "Yoooo Hot Dogs?",
        "Maybe grab a Cheeseburger?",
        "Maybe you should order in for this one"
        )

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
                if(!generating)
                {
                    lifecycleScope.launch {
                        repeat(20)
                        {
                            generating = true
                            snackText.setText(snacks[(0..(snacks.size - 1)).random()])
                            getRandomMovies()
                            posterPath = (MoviesRepo.randomMoviePoster())
                            Glide.with(poster)
                                    .load("https://image.tmdb.org/t/p/w342$posterPath")
                                    .into(poster)
                            movieText.text = (MoviesRepo.randomMovieTitle())
                            if(it >= 19)
                            {
                                generating = false
                            }
                            delay(100)
                        }
                    }
                }
            }
        }
    }

    private fun getRandomMovies() {
        MoviesRepo.getRandomMovies(
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
        Toast.makeText(this.context, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }
}
