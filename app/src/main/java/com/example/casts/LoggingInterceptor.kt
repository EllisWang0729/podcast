package com.example.casts

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request = chain.request()
        val t1 = System.nanoTime()
        Log.i(
            "LoggingInterceptor", String.format(
                "Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()
            )
        )
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.i(
            "LoggingInterceptor", String.format(
                "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )
        return response
    }
}