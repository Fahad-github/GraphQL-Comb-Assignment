package com.combyne.casestudy.di

import com.apollographql.apollo.ApolloClient
import com.combyne.casestudy.app.CombyneCaseStrudy
import com.combyne.casestudy.auth.AuthorizationInterceptor
import com.combyne.casestudy.constant.Constants.baseUrl
import com.combyne.casestudy.data.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModules {

    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient =
        ApolloClient.builder().serverUrl(baseUrl)
            .okHttpClient(
                OkHttpClient().newBuilder()
                    .addInterceptor(AuthorizationInterceptor()).build()
            ).build()

    @Singleton
    @Provides
    fun provideDataRepository(
        apolloClient: ApolloClient,
    ) = DataRepository(apolloClient)

}