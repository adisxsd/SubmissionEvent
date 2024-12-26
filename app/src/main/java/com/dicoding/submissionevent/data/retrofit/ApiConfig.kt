package com.dicoding.submissionevent.data.retrofit

import com.dicoding.submissionevent.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiConfig {
    companion object {
        @Volatile
        private var INSTANCE: ApiService? = null

        fun getApiService(): ApiService {
            return INSTANCE ?: synchronized(this) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                    else HttpLoggingInterceptor.Level.NONE
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://event-api.dicoding.dev/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                val apiService = retrofit.create(ApiService::class.java)
                INSTANCE = apiService
                apiService
            }
        }
    }
}