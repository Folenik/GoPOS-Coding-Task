package com.mosz.goposcodingtask.api

import com.mosz.goposcodingtask.BuildConfig
import com.mosz.goposcodingtask.model.AuthToken
import com.mosz.goposcodingtask.utilities.Constants
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.runBlocking
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TokenAuthenticator : Authenticator {
    companion object {
        var refreshToken: String? = null
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            var refresh = false
            if (!refreshToken.isNullOrEmpty()) {
                refresh = true
            }

            val myToken = getToken(refresh = refresh)
            refreshToken = myToken.blockingGet().refresh_token
            response.request.newBuilder()
                .header("Authorization", "Bearer ${myToken.blockingGet().access_token}")
                .build()
        }
    }

    private fun getToken(refresh: Boolean): Single<AuthToken> {
        val okHttpClient = OkHttpClient().newBuilder()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(APIService::class.java)
        if (!refresh) {
            return service.getToken(
                grantType = Constants.GRANT_TYPE_PASSWORD,
                username = BuildConfig.SERVER_LOGIN,
                password = BuildConfig.SERVER_PASSWORD,
                clientId = BuildConfig.SERVER_CLIENT_ID,
                clientSecret = BuildConfig.SERVER_CLIENT_SECRET
            )
        } else {
            return service.refreshToken(
                grantType = Constants.GRANT_TYPE_REFRESH,
                clientId = BuildConfig.SERVER_CLIENT_ID,
                clientSecret = BuildConfig.SERVER_CLIENT_SECRET,
                refreshToken = refreshToken ?: "null",
            )
        }
    }
}