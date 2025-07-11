package com.example.matrimonialmatch.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.matrimonialmatch.data.local.dao.UserDao
import com.example.matrimonialmatch.data.local.db.MatchMateDatabase
import com.example.matrimonialmatch.data.remote.api.UserApiService
import com.example.matrimonialmatch.data.repository.UserRepositoryImpl
import com.example.matrimonialmatch.data.util.FakeFlakyNetworkInterceptor
import com.example.matrimonialmatch.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository
}

@Module
@InstallIn(SingletonComponent::class)
object AppProvideModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        Log.d("HILT", "provideOkHttpClient: OkHttpClient created")
        return OkHttpClient.Builder()
            .addInterceptor(FakeFlakyNetworkInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MatchMateDatabase {
        return Room.databaseBuilder(
            context,
            MatchMateDatabase::class.java,
            "matchmate_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: MatchMateDatabase): UserDao {
        return database.userDao()
    }
}

