package com.example.binge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_TRAILER = "extra_movie_trailer"
const val MOVIE_ID = "extra_movie_id"

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var ratingText: TextView
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var trailer: TextView
    private lateinit var trailerList: List<Trailer>
    private lateinit var recyclerView: RecyclerView
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        api = retrofit.create(Api::class.java)
    }

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
        trailer = findViewById(R.id.trailer)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
        setTitle("Movie Details")

        initView()
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
        trailer.text = extras.getString(MOVIE_ID, "")
    }

    private fun initView()
    {
        trailerList = ArrayList<Trailer>()
        adapter = TrailerAdapter(this, trailerList)
        recyclerView = findViewById(R.id.recycler_view)
        var mLayoutMgr: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutMgr
        recyclerView.adapter = adapter

        LoadJSON()
    }

    private fun LoadJSON()
    {
        try {
            var call: Call<GetTrailerResponse> = api.getMovieTrailer("9e1b281c7090884268f7269a0f11525f", MOVIE_ID.toInt())
                    call.enqueue(object : Callback<GetTrailerResponse> {
                        override fun onResponse(
                                call: Call<GetTrailerResponse>,
                                response: Response<GetTrailerResponse>
                        )
                        {
                            var _trailer: List<Trailer> = response.body()!!.getResults()
                            recyclerView.adapter = TrailerAdapter(applicationContext, _trailer)
                            recyclerView.smoothScrollToPosition(0)
                        }

                        override fun onFailure(call: Call<GetTrailerResponse>, t: Throwable) {
                            Log.d("ERROR", t.message.toString())
                            Toast.makeText(this@MovieDetailsActivity, "Error fetching trailer data", Toast.LENGTH_SHORT).show()
                        }
                    })
        }catch (e: Exception)
        {
            Log.d("Error", e.message.toString())
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }


}
