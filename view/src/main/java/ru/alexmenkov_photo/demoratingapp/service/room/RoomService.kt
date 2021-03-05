package ru.alexmenkov_photo.demoratingapp.service.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.alexmenkov_photo.demoratingapp.service.room.dao.LotDao
import ru.alexmenkov_photo.demoratingapp.service.room.dao.RemoteKeyDao
import ru.alexmenkov_photo.demoratingapp.service.room.entity.Lot
import ru.alexmenkov_photo.demoratingapp.service.room.entity.RemoteKey

@Database(
    entities = [Lot::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RoomService : RoomDatabase() {

    abstract fun lotDao(): LotDao
    abstract fun remoteKeyDao(): RemoteKeyDao?

    companion object {
        private var instance: RoomService? = null

        @Synchronized
        fun getInstance(context: Context): RoomService {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    RoomService::class.java, "demoratingapp_database"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
            return instance as RoomService
        }
    }
}