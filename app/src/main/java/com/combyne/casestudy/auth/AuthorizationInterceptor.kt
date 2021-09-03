package com.combyne.casestudy.auth

import com.combyne.casestudy.constant.Constants.xParseApplicationId
import com.combyne.casestudy.constant.Constants.xParseMasterKey
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-Parse-Application-Id", xParseApplicationId)
            .addHeader("X-Parse-Master-Key", xParseMasterKey)
            .build()
        return chain.proceed(request)
    }
}