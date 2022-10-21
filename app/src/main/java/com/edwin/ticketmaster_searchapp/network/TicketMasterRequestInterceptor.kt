package com.edwin.ticketmaster_searchapp.network

import com.edwin.ticketmaster_searchapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TicketMasterRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val initialUrl = initialRequest.url()
        val newUrl = initialUrl
            .newBuilder()
            .addQueryParameter(KEY_TICKETMASTER_API_KEY, BuildConfig.TICKETMASTER_API_KEY)
            .build()
        val newRequest = initialRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val KEY_TICKETMASTER_API_KEY = "apikey"
    }
}