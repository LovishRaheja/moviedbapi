package com.example.moviedbapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviedbapp.R
import com.example.moviedbapp.data.NetworkState
import com.example.moviedbapp.data.api.TheMovieDbInterface
import com.example.moviedbapp.data.client.TheMovieDbClient
import com.example.moviedbapp.data.repository.MovieDetailsRepository
import com.example.moviedbapp.data.repository.MoviePageListRepository
import com.example.moviedbapp.ui.viewmodel.MainActivityViewModel
import com.example.moviedbapp.ui.viewmodel.MovieDetailViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_movie_detail.*

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var movieRepository: MoviePageListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiService: TheMovieDbInterface =
            TheMovieDbClient.getClient()
        movieRepository =
            MoviePageListRepository(
                apiService
            )

        viewModel = getViewModel()

        val movieAdapter = MoviePageListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }

        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility = if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if(!viewModel.listIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })

    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(
                    movieRepository
                ) as T
            }
        })[MainActivityViewModel::class.java]

    }
}
