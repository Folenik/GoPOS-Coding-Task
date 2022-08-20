package com.mosz.goposcodingtask.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        startApplication()
    }

    private fun startApplication() {
        val intent = Intent(this, ItemsActivity::class.java)
        startActivity(intent)
        finish()
    }
}