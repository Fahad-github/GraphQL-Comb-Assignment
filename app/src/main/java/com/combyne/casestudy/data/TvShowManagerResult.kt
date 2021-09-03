package com.combyne.casestudy.data

sealed class TvShowManagerResult<T>(
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data: T) : TvShowManagerResult<T>(data)
    class Error<T>(message: String, data: T? = null) : TvShowManagerResult<T>(data, message)
    class Loading<T> : TvShowManagerResult<T>()
}
