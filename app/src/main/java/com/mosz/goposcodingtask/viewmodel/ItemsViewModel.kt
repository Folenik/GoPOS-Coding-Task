package com.mosz.goposcodingtask.viewmodel

import androidx.lifecycle.ViewModel
import com.mosz.goposcodingtask.model.submodels.Data
import com.mosz.goposcodingtask.repositories.ItemsRepository
import com.mosz.goposcodingtask.utilities.Constants
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject

class ItemsViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val itemsSubject: PublishSubject<ItemsViewModelEvent> = PublishSubject.create()

    fun getItems() {
        itemsRepository.getItems(
            tax = Constants.INCLUDE_TAX,
            category = Constants.INCLUDE_CATEGORY
        )
            .doOnSubscribe {
                itemsSubject.onNext(
                    ItemsViewModelEvent(
                        type = ItemsViewModelEvent.Type.LOADING
                    )
                )
            }
            .subscribe({
                itemsSubject.onNext(
                    ItemsViewModelEvent(
                        type = ItemsViewModelEvent.Type.DATA_LOADED,
                        items = it.data,
                    )
                )
            }, {
                itemsSubject.onNext(
                    ItemsViewModelEvent(
                        type = ItemsViewModelEvent.Type.ERROR,
                        error = it
                    )
                )
            })
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}

data class ItemsViewModelEvent(
    val items: List<Data> = emptyList(),
    val type: Type,
    val error: Throwable = Throwable()
) {
    enum class Type {
        LOADING, DATA_LOADED, ERROR
    }
}