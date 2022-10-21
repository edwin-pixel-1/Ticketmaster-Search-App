package com.edwin.ticketmaster_searchapp.di

import android.content.Context
import com.edwin.ticketmaster_searchapp.BuildConfig
import com.edwin.ticketmaster_searchapp.manager.NetworkManager
import com.edwin.ticketmaster_searchapp.network.TicketMasterRequestInterceptor
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.TicketMasterRepository
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.local.LocalStorage
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.local.LocalStorageImp
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.remote.RemoteStorage
import com.edwin.ticketmaster_searchapp.repository.ticketmaster.datasource.remote.TicketMasterAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    fun provideTicketMasterRequestInterceptor(): TicketMasterRequestInterceptor =
        TicketMasterRequestInterceptor()

    @Provides
    fun provideOkHttpClient(ticketMasterRequestInterceptor: TicketMasterRequestInterceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ticketMasterRequestInterceptor)
            .cache(null)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideTicketMasterAPIService(retrofit: Retrofit): TicketMasterAPIService =
        retrofit.create(TicketMasterAPIService::class.java)

    @Provides
    fun provideTicketMasterRepository(
        remoteStorage: RemoteStorage,
        localStorage: LocalStorage,
        networkManager: NetworkManager
    ): TicketMasterRepository = TicketMasterRepository(remoteStorage, localStorage, networkManager)

    @Provides
    fun provideTicketMasterLocalStorage(@ApplicationContext context: Context): LocalStorage =
        LocalStorageImp(context)

    @Provides
    fun provideTicketMasterRemoteStorage(ticketMasterAPIService: TicketMasterAPIService): RemoteStorage =
        RemoteStorage(ticketMasterAPIService)

    @Provides
    fun provideNetworkManager(@ApplicationContext context: Context) : NetworkManager = NetworkManager(context)
}