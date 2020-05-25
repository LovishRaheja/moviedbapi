package com.example.moviedbapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviedbapp.data.api.TheMovieDbInterface
import com.example.moviedbapp.model.MovieDetail
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailNetwork(
    private val apiService: TheMovieDbInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieResponse = MutableLiveData<MovieDetail>()
    val downloadedMovieResponse: LiveData<MovieDetail>
        get() = _downloadedMovieResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)

        try {

            compositeDisposable.add(apiService.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _downloadedMovieResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    }, {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("Error in response", it.message)
                    }
                )
            )


        } catch (e: Exception) {
            Log.e("Error in response", e.message)
        }
    }

}