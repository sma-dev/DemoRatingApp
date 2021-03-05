package ru.alexmenkov_photo.demoratingapp

import android.app.Application
import ru.alexmenkov_photo.demoratingapp.di.AppComponent
import ru.alexmenkov_photo.demoratingapp.di.DaggerAppComponent

open class MainApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(
                applicationContext
            )
    }

}
