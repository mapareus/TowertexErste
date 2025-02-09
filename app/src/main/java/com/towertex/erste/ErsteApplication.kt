package com.towertex.erste

import android.app.Application
import com.towertex.erste.di.repositoryModule
import com.towertex.erste.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ErsteApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ErsteApplication)
            modules(listOf(repositoryModule, viewModelModule))
        }
    }
}