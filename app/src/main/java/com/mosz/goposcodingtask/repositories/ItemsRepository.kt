package com.mosz.goposcodingtask.repositories

import com.mosz.goposcodingtask.api.APIService
import com.mosz.goposcodingtask.model.Items
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ItemsRepository(private val apiService: APIService) {

    fun getItems(startDate: String, endDate: String): Single<Items> {
        return apiService.getItems(startDate, endDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}