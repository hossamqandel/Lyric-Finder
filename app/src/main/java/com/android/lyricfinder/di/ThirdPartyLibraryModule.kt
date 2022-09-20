package com.android.lyricfinder.di

import android.content.Context
import androidx.room.Room
import com.android.lyricfinder.feature_base.data.local.LyricFinderDatabase
import com.android.lyricfinder.feature_base.data.local.LyricFinderDatabase.Companion.DATABASE_NAME
import com.android.lyricfinder.feature_base.data.remote.LyricFinderService
import com.android.lyricfinder.utils.Const
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ThirdPartyLibraryModule {

    @Provides
    @Singleton
    fun provideLyricDatabase(@ApplicationContext context: Context): LyricFinderDatabase {
        val databaseBuilder by lazy { Room.databaseBuilder(
                context,
                LyricFinderDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
        return databaseBuilder
    }

    @Provides
    @Singleton
    fun provideLyricFinderService(): LyricFinderService {
        val client = OkHttpClient.Builder()
            .connectTimeout(50, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(150, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(50, java.util.concurrent.TimeUnit.SECONDS)
            .callTimeout(50, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(Interceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url
                val url = originalUrl.newBuilder().build()
                val requestBuilder = originalRequest.newBuilder().url(url)
                    .addHeader("X-RapidAPI-Key", "${Const.API_KEY}")
                    .addHeader("X-RapidAPI-Host", "${Const.API_HOST}")
                val request = requestBuilder.build()
                val response = chain.proceed(request)
                response.code //status code
                response
            })
            .build()

        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(LyricFinderService::class.java)
    }
}