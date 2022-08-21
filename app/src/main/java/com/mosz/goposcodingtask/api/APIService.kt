package com.mosz.goposcodingtask.api

import com.mosz.goposcodingtask.model.AuthToken
import com.mosz.goposcodingtask.model.Items
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @GET("/api/v3/27/items")
    fun getItems(
        @Query("include") includeTax: String,
        @Query("include") includeCategory: String,
    ): Single<Items>

    @POST("oauth/token?")
    fun getToken(
        @Query("grant_type") grantType: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
    ): Single<AuthToken>

    @POST("oauth/token?")
    fun refreshToken(
        @Query("grant_type") grantType: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("refresh_token") refreshToken: String
    ): Single<AuthToken>
}
