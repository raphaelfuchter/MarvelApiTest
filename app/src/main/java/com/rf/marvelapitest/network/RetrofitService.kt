package com.rf.marvelapitest.network

import com.rf.marvelapitest.models.MarvelEndPoints.BASE_URL
import com.rf.marvelapitest.models.MarvelEndPoints.PRIVATE_API_KEY
import com.rf.marvelapitest.models.MarvelEndPoints.PUBLIC_API_KEY

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitService {
    companion object {

        private var retrofit: Retrofit? = null

        private fun getRetrofit(): Retrofit {
            if (retrofit == null) {
                val httpClient = OkHttpClient.Builder()
                httpClient.readTimeout(30, TimeUnit.SECONDS)
                httpClient.connectTimeout(30, TimeUnit.SECONDS)
                httpClient.writeTimeout(30, TimeUnit.SECONDS)
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build()
            }
            return retrofit!!
        }

        fun getApiService(): MarvelAPI {
            return getRetrofit().create(MarvelAPI::class.java)
        }

        fun getHash(): String? {
            return Criptografia.md5(getTimeStamp() + PRIVATE_API_KEY + PUBLIC_API_KEY)
        }

    }
}