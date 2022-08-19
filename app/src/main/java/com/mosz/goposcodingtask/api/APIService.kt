package com.mosz.goposcodingtask.api

import com.mosz.goposcodingtask.model.Items
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("items")
    fun getItems(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Single<Items>
}
