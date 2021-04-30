package com.example.binge.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binge.*
import com.example.binge.Activities.*
import com.example.binge.Adapters.MoviesAdapter
import com.example.binge.Extras.MoviesRepo
import com.example.binge.Models.MovieModel

class HomeFragment : Fragment() {

    private lateinit var popularMovies: RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MoviesAdapter
    private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

    private var popularMoviesPage = 1
    private var topRatedMoviesPage = 1
    private var upcomingMoviesPage = 1

    override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        initPopularMovies(v)
        initTopRatedMovies(v)
        initUpcomingMovies(v)

        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()
        return v;
    }

    private fun initPopularMovies(v: View)
    {
        popularMovies = v.findViewById(R.id.popular_movies)
        popularMoviesLayoutMgr = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        popularMovies.layoutManager = popularMoviesLayoutMgr
        popularMoviesAdapter =
            MoviesAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
        popularMovies.adapter = popularMoviesAdapter
    }

    private fun initTopRatedMovies(v: View)
    {
        topRatedMovies = v.findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutMgr = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter =
            MoviesAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
        topRatedMovies.adapter = topRatedMoviesAdapter
    }

    private fun initUpcomingMovies(v: View)
    {
        upcomingMovies = v.findViewById(R.id.upcoming_movies)
        upcomingMoviesLayoutMgr = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
        upcomingMoviesAdapter =
            MoviesAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
        upcomingMovies.adapter = upcomingMoviesAdapter
    }

    private fun getPopularMovies() {
        MoviesRepo.getPopularMovies(
            popularMoviesPage,
            ::showPopularMovies,
            ::showError
        )
    }

    private fun getTopRatedMovies() {
        MoviesRepo.getTopRatedMovies(
            topRatedMoviesPage,
            ::showTopRatedMovies,
            ::showError
        )
    }

    private fun getUpcomingMovies() {
        MoviesRepo.getUpcomingMovies(
            upcomingMoviesPage,
            ::showUpcomingMovies,
            ::showError
        )
    }

    private fun popularMoviesOnScrollListener() {
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (popularMoviesLayoutMgr.findFirstVisibleItemPosition() +
                    popularMoviesLayoutMgr.childCount >= popularMoviesLayoutMgr.itemCount / 2) {
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun topRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (topRatedMoviesLayoutMgr.findFirstVisibleItemPosition() +
                    topRatedMoviesLayoutMgr.childCount >= topRatedMoviesLayoutMgr.itemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun upcomingMoviesOnScrollListener() {
        upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (upcomingMoviesLayoutMgr.findFirstVisibleItemPosition() +
                    upcomingMoviesLayoutMgr.childCount >= upcomingMoviesLayoutMgr.itemCount / 2) {
                    upcomingMovies.removeOnScrollListener(this)
                    upcomingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }

    private fun showPopularMovies(movieModels: List<MovieModel>) {
        popularMoviesAdapter.appendMovies(movieModels)
        popularMoviesOnScrollListener()
    }

    private fun showTopRatedMovies(movieModels: List<MovieModel>) {
        topRatedMoviesAdapter.appendMovies(movieModels)
        topRatedMoviesOnScrollListener()
    }

    private fun showUpcomingMovies(movieModels: List<MovieModel>) {
        upcomingMoviesAdapter.appendMovies(movieModels)
        upcomingMoviesOnScrollListener()
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

    private fun showError() {
        Toast.makeText(this.context, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
    }
}
