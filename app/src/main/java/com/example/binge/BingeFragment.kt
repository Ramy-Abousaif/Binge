package com.example.binge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class BingeFragment: Fragment(), View.OnClickListener {

    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var text: TextView
    private var generate: Boolean = false
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

    private var popularMoviesPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_binge, container, false)
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        text = v.findViewById(R.id.snack_recommendation) as TextView
        val btn: Button = v.findViewById(R.id.button_binge)
        btn.setOnClickListener(this)
        return v;
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_binge -> {
                text.setText(snacks[(0..(snacks.size - 1)).random()])
                if(!generate)
                {
                    getRandomMovies()
                    generate = true;
                }
            }
        }
    }

    private fun getRandomMovies() {
        MoviesRepository.getRandomMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun attachPopularMoviesOnScrollListener() {
        popularMoviesPage = (0..32220).random()
        getRandomMovies()
    }


    private fun onPopularMoviesFetched(movie: Movie) {
        popularMoviesAdapter.appendMovie(movie)
        attachPopularMoviesOnScrollListener()
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this.context, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_ID, movie.id)
        startActivity(intent)
    }

    private fun onError() {
        Toast.makeText(this.context, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}
