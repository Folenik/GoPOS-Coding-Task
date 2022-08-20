package com.mosz.goposcodingtask.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mosz.goposcodingtask.BuildConfig
import com.mosz.goposcodingtask.api.APIService
import com.mosz.goposcodingtask.repositories.ItemsRepository
import com.mosz.goposcodingtask.utilities.Constants
import com.mosz.goposcodingtask.viewmodel.ItemsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    fun provideItemsRepository(apiServices: APIService) = ItemsRepository(apiServices)

    single { provideItemsRepository(get()) }
}

val viewModelModule = module {
    fun provideItemsViewModel(repository: ItemsRepository) = ItemsViewModel(repository)

    viewModel { provideItemsViewModel(get()) }
}

val restModule = module {
    val connectTimeout: Long = Constants.BASE_TIMEOUT
    val readTimeout: Long = Constants.BASE_TIMEOUT
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun provideHttpClient() = OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor {
            it.request().newBuilder()
                //.header(API_KEY_HEADER, BuildConfig.API_KEY)
                .build()
                .let { request ->
                    it.proceed(request)
                }
        }.build()

    fun provideRetrofit(client: OkHttpClient, serverUrl: String) = Retrofit.Builder()
        .baseUrl(serverUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client)
        .build()

    single<Gson> { GsonBuilder().create() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), BuildConfig.SERVER_URL) }

    single<APIService> { get<Retrofit>().create(APIService::class.java) }
}

val allModules = repositoryModule + viewModelModule + restModule