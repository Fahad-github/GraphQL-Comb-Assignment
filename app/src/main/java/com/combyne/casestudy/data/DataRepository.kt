package com.combyne.casestudy.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.combyne.casestudy.CreatMoviesMutation
import com.combyne.casestudy.GetMoviesQuery
import com.combyne.casestudy.type.MovieOrder
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {


    suspend fun getMovies(): TvShowManagerResult<GetMoviesQuery.Movies> {

        //FOR GETTING THE MOVIES IN DESC

        val movieOrderList = ArrayList<MovieOrder>().also {
            it.add(MovieOrder.CREATEDAT_DESC)
        }
        val movieOrder: Input<List<MovieOrder>> = Input.optional(movieOrderList)

        return try {
            val response = apolloClient.query(GetMoviesQuery(movieOrder)).await()

            if (response.hasErrors()) {
                TvShowManagerResult.Error("An unknown error occured", null)
            } else {
                TvShowManagerResult.Success(response.data?.movies!!)
            }
        } catch (e: Exception) {
            TvShowManagerResult.Error(
                "Couldn't reach the server. check your internet connection",
                null
            )
        }
    }

    suspend fun createMovie(movie: CreatMoviesMutation): TvShowManagerResult<CreatMoviesMutation.CreateMovie> {
        return try {
            val mutation = CreatMoviesMutation(movie.title, movie.releaseDate, movie.seasons)
            val response = apolloClient.mutate(mutation).await()
            if (response.hasErrors()) {
                TvShowManagerResult.Error("An unknown error occured", null)
            } else {
                TvShowManagerResult.Success(response.data?.createMovie!!)
            }
        } catch (e: Exception) {
            TvShowManagerResult.Error(
                "Couldn't reach the server. check your internet connection",
                null
            )
        }


    }
}