package com.example.matrimonialmatch.data.util

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class FakeFlakyNetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val shouldFail = (0..100).random() < 30 // 30% chance to fail
        if (shouldFail) {
            throw IOException("Simulated flaky network failure")
        }
        return chain.proceed(chain.request())
    }
}
