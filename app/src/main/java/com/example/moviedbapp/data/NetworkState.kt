package com.example.moviedbapp.data

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {

    companion object {
        var LOADED: NetworkState
        var LOADING: NetworkState
        var ERROR: NetworkState
        var ENDOFLIST: NetworkState


        init {
            LOADED =
                NetworkState(
                    Status.SUCCESS,
                    "Success"
                )
            LOADING =
                NetworkState(
                    Status.RUNNING,
                    "Running"
                )
            ERROR =
                NetworkState(
                    Status.FAILED,
                    "Something went wrong"
                )
            ENDOFLIST =
                NetworkState(
                    Status.FAILED,
                    "End of page"
                )
        }
    }

}