package com.combyne.casestudy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combyne.casestudy.GetMoviesQuery
import com.combyne.casestudy.data.DataRepository
import com.combyne.casestudy.data.TvShowManagerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val movies: MutableLiveData<TvShowManagerResult<GetMoviesQuery.Movies>> = MutableLiveData()

    init {
        getMovies()
    }

    private fun getMovies() = viewModelScope.launch {
        getMoviesFromQuery()
    }

    private suspend fun getMoviesFromQuery() {
        movies.postValue(TvShowManagerResult.Loading())
        try {
            val response = repository.getMovies()
            response.data?.let {
                movies.postValue(TvShowManagerResult.Success(it))
            }
            movies.postValue(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> movies.postValue(TvShowManagerResult.Error("Network Error"))
                else -> movies.postValue(TvShowManagerResult.Error("Conversion Error"))
            }
        }

    }

}