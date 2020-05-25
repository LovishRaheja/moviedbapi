package com.example.moviedbapp.data.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviedbapp.data.NetworkState
import com.example.moviedbapp.data.api.TheMovieDbInterface
import com.example.moviedbapp.data.repository.MovieDataSource
import com.example.moviedbapp.model.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val apiService: TheMovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {


    val movieLiveDataSource: MutableLiveData<MovieDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }

}