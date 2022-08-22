package com.mosz.goposcodingtask.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mosz.goposcodingtask.R
import com.mosz.goposcodingtask.databinding.ActivityItemsBinding
import com.mosz.goposcodingtask.item.FoodItem
import com.mosz.goposcodingtask.item.ProgressItem
import com.mosz.goposcodingtask.model.submodels.Data
import com.mosz.goposcodingtask.utilities.ConnectionUtil
import com.mosz.goposcodingtask.utilities.Constants
import com.mosz.goposcodingtask.viewmodel.ItemsViewModel
import com.mosz.goposcodingtask.viewmodel.ItemsViewModelEvent
import io.objectbox.BoxStore
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class ItemsActivity : AppCompatActivity() {
    private val viewModel: ItemsViewModel by viewModel()
    private val boxStore: BoxStore by inject()
    private lateinit var binding: ActivityItemsBinding
    private val itemAdapter = GenericItemAdapter()
    private val footerAdapter: ItemAdapter<ProgressItem> = ItemAdapter.items()
    private val fastAdapter = FastAdapter.with(listOf(itemAdapter, footerAdapter))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_items)

        setup()
        checkForInternetConnection()
    }

    private fun setup() {
        binding.list.apply {
            setItemViewCacheSize(Constants.ITEM_VIEW_CACHE)
            adapter = fastAdapter
            layoutManager = LinearLayoutManager(context)
            adapter?.apply {
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
        }
    }

    private fun checkForInternetConnection() {
        if (ConnectionUtil.isNetworkAvailable(baseContext)) {
            getItemsFromServer()
        } else {
            getItemsFromDatabase()
        }
    }

    private fun getItemsFromServer() {
        viewModel.itemsSubject.subscribe(::handleEvent)
        viewModel.getItems()
    }

    private fun getItemsFromDatabase() {
        boxStore.boxFor(Data::class.java).all.forEach {
            itemAdapter.add(
                FoodItem(
                    it.itemName ?: "",
                    it.itemCategory?.get("name") ?: "",
                    it.itemPriceAmount?.get("amount")?.toDouble() ?: 0.0,
                    it.itemPriceAmount?.get("currency") ?: "",
                    it.itemTax?.get("name") ?: "",
                    it.itemImageLink?.smallLink ?: ""
                )
            )
        }
    }

    private fun addItemsToDatabase(items: List<Data>) {
        for (item in items) {
            if (!boxStore.boxFor(Data::class.java).all.contains(item)) {
                boxStore.boxFor(Data::class.java).put(item)
            }
        }
        getItemsFromDatabase()
    }


    private fun handleEvent(event: ItemsViewModelEvent) {
        when (event.type) {
            ItemsViewModelEvent.Type.LOADING -> {
                footerAdapter.add(ProgressItem())
            }
            ItemsViewModelEvent.Type.DATA_LOADED -> {
                footerAdapter.clear()
                addItemsToDatabase(event.items)
            }
            ItemsViewModelEvent.Type.ERROR -> {
                footerAdapter.clear()
                Snackbar.make(binding.root, event.error.message.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}