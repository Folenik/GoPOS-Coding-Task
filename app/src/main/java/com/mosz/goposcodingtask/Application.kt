package com.mosz.goposcodingtask

import android.app.Application
import com.mosz.goposcodingtask.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(allModules)
        }
    }
}