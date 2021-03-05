package ru.alexmenkov_photo.demoratingapp.vmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import ru.alexmenkov_photo.demoratingapp.MainApplication
import ru.alexmenkov_photo.demoratingapp.service.room.entity.Lot
import javax.inject.Inject
import javax.inject.Named

class HomeViewModel(application: Application) : AndroidViewModel(application)/*TODO replace by abstract BaseVM*/ {

    init {
        (application as MainApplication).appComponent.inject(this)
    }

    @Inject
    @Named("default")
    lateinit var catalogPager: Pager<Int, Lot>

}