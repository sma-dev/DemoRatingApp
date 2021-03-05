package ru.alexmenkov_photo.demoratingapp.service.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.alexmenkov_photo.demoratingapp.service.room.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKey)

    @Query("SELECT * FROM _remote_key")
    suspend fun getRemoteKey(): RemoteKey?

    @Query("SELECT * FROM _remote_key k WHERE k.tag=:tag")
    suspend fun getRemoteKeyByTag(tag: String): RemoteKey?

    @Query("DELETE FROM _remote_key")
    suspend fun clearAll()

    @Query("DELETE FROM _remote_key WHERE tag=:tag")
    suspend fun deleteByTag(tag: String)
}