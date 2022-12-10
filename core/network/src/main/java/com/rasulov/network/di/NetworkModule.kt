package com.rasulov.network.di

import com.rasulov.network.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
internal class NetworkModule {


    private fun getMoshi(): Moshi = Moshi.Builder().build()

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthorizationInterceptor())
            .addInterceptor(createLoggingInterceptor())
            .build()
    }


    private fun createAuthorizationInterceptor(): Interceptor {
        val token = "Bearer " + BuildConfig.BEARER_TOKEN
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            newBuilder.addHeader("Authorization", token)
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }

    private fun createLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3")
            .client(getOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .build()
    }
}