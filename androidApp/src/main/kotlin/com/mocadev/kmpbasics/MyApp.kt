package com.mocadev.kmpbasics

import android.app.Application
import com.mocadev.kmpbasics.di.androidModule
import com.mocadev.kmpbasics.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(
                commonModule,
                androidModule
            )
        }
    }
}