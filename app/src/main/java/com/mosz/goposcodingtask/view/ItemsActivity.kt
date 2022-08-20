package com.mosz.goposcodingtask.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.mosz.goposcodingtask.R
import com.mosz.goposcodingtask.databinding.ActivityItemsBinding
import com.mosz.goposcodingtask.utilities.CalendarUtils
import com.mosz.goposcodingtask.viewmodel.ItemsViewModel
import com.mosz.goposcodingtask.viewmodel.ItemsViewModelEvent
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemsActivity : AppCompatActivity() {

    private val viewModel: ItemsViewModel by viewModel()
    private lateinit var binding: ActivityItemsBinding
    private var nextEndDate: String = CalendarUtils.today()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_items)

        testApi()
    }

    private fun testApi() {
        viewModel.itemsSubject.subscribe(::handleEvent)
        viewModel.getItems(nextEndDate)
    }

    private fun handleEvent(event: ItemsViewModelEvent) {
        when (event.type) {
            ItemsViewModelEvent.Type.LOADING -> {
                Log.i("MYLOG","loading")
            }
            ItemsViewModelEvent.Type.DATA_LOADED -> {
                Log.i("MYLOG",event.items.toString())
                nextEndDate = event.nextEndDate
            }
            ItemsViewModelEvent.Type.ERROR -> {
                Log.i("MYLOG",event.error.message.toString())
            }
        }
    }
}