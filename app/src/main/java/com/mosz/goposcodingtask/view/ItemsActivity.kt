package com.mosz.goposcodingtask.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.internal.LinkedTreeMap
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
import org.koin.androidx.viewmodel.ext.android.viewModel


class ItemsActivity : AppCompatActivity() {
    private val viewModel: ItemsViewModel by viewModel()
    private lateinit var binding: ActivityItemsBinding
    private val itemAdapter = GenericItemAdapter()
    private val footerAdapter: ItemAdapter<ProgressItem> = ItemAdapter.items()
    private val fastAdapter = FastAdapter.with(listOf(itemAdapter, footerAdapter))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_items)

        setup()
        //checkForInternetConnection()
        testApi()
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
        //server
    }

    private fun getItemsFromDatabase() {
        //database
    }

    private fun testApi() {
        viewModel.itemsSubject.subscribe(::handleEvent)
        viewModel.getItems()
    }

    private fun addItems(items: List<Data>) {
        items.forEach {
            itemAdapter.add(FoodItem(it.name, it.category.name, it.price.amount, it.price.currency, it.tax.name, it.image_link?.default_link ?: ""))
        }
    }

    private fun handleEvent(event: ItemsViewModelEvent) {
        when (event.type) {
            ItemsViewModelEvent.Type.LOADING -> {
                footerAdapter.add(ProgressItem())
            }
            ItemsViewModelEvent.Type.DATA_LOADED -> {
                footerAdapter.clear()
                addItems(event.items)
            }
            ItemsViewModelEvent.Type.ERROR -> {
                footerAdapter.clear()
                Snackbar.make(binding.root, event.error.message.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}