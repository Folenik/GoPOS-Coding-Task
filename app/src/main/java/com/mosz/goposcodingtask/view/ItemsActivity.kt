package com.mosz.goposcodingtask.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mosz.goposcodingtask.R
import com.mosz.goposcodingtask.databinding.ActivityItemsBinding
import com.mosz.goposcodingtask.utilities.ConnectionUtil
import com.mosz.goposcodingtask.viewmodel.ItemsViewModel
import com.mosz.goposcodingtask.viewmodel.ItemsViewModelEvent
import org.koin.androidx.viewmodel.ext.android.viewModel


class ItemsActivity : AppCompatActivity() {
    private val viewModel: ItemsViewModel by viewModel()
    private lateinit var binding: ActivityItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_items)

        setup()
        checkForInternetConnection()
        testApi()
    }

    private fun setup() {
        //setup całego UI
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


    private fun handleEvent(event: ItemsViewModelEvent) {
        when (event.type) {
            ItemsViewModelEvent.Type.LOADING -> {
                Log.i("MYLOG", "loading")
            }
            ItemsViewModelEvent.Type.DATA_LOADED -> {
                Log.i("MYLOG", event.items.toString())
                Log.i("MYLOG", event.toString())
            }
            ItemsViewModelEvent.Type.ERROR -> {
                Log.i("MYLOG", event.error.message.toString())
            }
        }
    }
}