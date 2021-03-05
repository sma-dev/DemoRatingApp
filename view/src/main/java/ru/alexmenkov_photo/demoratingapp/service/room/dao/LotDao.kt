package ru.alexmenkov_photo.demoratingapp.service.room.dao

import androidx.paging.PagingSource
import androidx.room.*
import ru.alexmenkov_photo.demoratingapp.service.room.entity.Lot

@Dao
interface LotDao {

    @Query("SELECT * FROM lot")
    fun getAll(): List<Lot>

    @Query("SELECT * FROM lot")
    fun basePagingSource(): PagingSource<Int, Lot>

    @Insert
    fun insert(lot: Lot)

    @Update
    fun update(lot: Lot)

    @Delete
    fun delete(lot: Lot)

    @Query("DELETE FROM lot")
    fun clearAll()
}