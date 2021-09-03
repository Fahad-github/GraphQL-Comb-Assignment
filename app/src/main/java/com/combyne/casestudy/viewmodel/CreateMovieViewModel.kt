package com.combyne.casestudy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.combyne.casestudy.CreatMoviesMutation
import com.combyne.casestudy.GetMoviesQuery
import com.combyne.casestudy.data.DataRepository
import com.combyne.casestudy.data.TvShowManagerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CreateMovieViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val createdMovie: MutableLiveData<TvShowManagerResult<CreatMoviesMutation.CreateMovie>> = MutableLiveData()

    fun createMovie(movie:CreatMoviesMutation) = viewModelScope.launch {
        initCreateMovie(movie = movie)
    }

    private suspend fun initCreateMovie(movie: CreatMoviesMutation) {
        createdMovie.postValue(TvShowManagerResult.Loading())
        try {
            val response = repository.createMovie(movie = movie)
            response.data?.let {
                createdMovie.postValue(TvShowManagerResult.Success(it))
            }
            createdMovie.postValue(response)
        } catch (t: Throwable) {
            when (t) {
                is IOException -> createdMovie.postValue(TvShowManagerResult.Error("Network Error"))
                else -> createdMovie.postValue(TvShowManagerResult.Error("Conversion Error"))
            }
        }
    }


}