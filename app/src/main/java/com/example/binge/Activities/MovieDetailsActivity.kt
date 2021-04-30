package com.example.binge.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.binge.R

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"

class MovieDetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var ratingText: TextView
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        ratingBar = findViewById(R.id.movie_rating_bar)
        ratingText = findViewById(R.id.movie_rating_text)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

        val btn: Button = findViewById(R.id.button_link)
        btn.setOnClickListener(this)

        setTitle("Movie Details")
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                    .transform(CenterCrop())
                    .into(backdrop)
        }

        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w342$posterPath")
                    .transform(CenterCrop())
                    .into(poster)
        }

        title.text = extras.getString(MOVIE_TITLE, "")
        ratingBar.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        ratingText.setText((extras.getFloat(MOVIE_RATING, 0f)).toString() + "/" + "10")
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_link -> {
                var searchTitle: String = title.text.toString().replace(" ", "+")
                var finalTitle: String = searchTitle.replace("'", "") + "+trailer"
                goToURL("https://www.youtube.com/results?search_query=" + finalTitle)
            }
        }
    }

    fun goToURL(s: String)
    {
        var uri: Uri = Uri.parse(s)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}
