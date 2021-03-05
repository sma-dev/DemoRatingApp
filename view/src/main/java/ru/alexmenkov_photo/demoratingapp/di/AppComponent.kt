package ru.alexmenkov_photo.demoratingapp.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.alexmenkov_photo.demoratingapp.ui.MainActivity
import ru.alexmenkov_photo.demoratingapp.vmodel.HomeViewModel
import javax.inject.Singleton


@Singleton
@Component(modules = [PagingSourceModule::class, RemoteApiModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(homeViewModel: HomeViewModel)
}