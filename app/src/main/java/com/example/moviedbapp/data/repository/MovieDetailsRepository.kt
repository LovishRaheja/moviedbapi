package com.example.moviedbapp.data.repository

import androidx.lifecycle.LiveData
import com.example.moviedbapp.data.NetworkState
import com.example.moviedbapp.data.api.TheMovieDbInterface
import com.example.moviedbapp.model.MovieDetail
import com.example.moviedbapp.data.MovieDetailNetwork
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: TheMovieDbInterface) {
    private lateinit var movieDetailNetwork: MovieDetailNetwork

    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetail> {

        movieDetailNetwork = MovieDetailNetwork(
            apiService,
            compositeDisposable
        )
        movieDetailNetwork.fetchMovieDetails(movieId)

        return movieDetailNetwork.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailNetwork.networkState

    }
}