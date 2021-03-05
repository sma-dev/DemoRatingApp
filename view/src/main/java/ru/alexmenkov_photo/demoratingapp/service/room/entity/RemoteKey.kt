package ru.alexmenkov_photo.demoratingapp.service.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "_remote_key")
data class RemoteKey(
    @PrimaryKey
    val tag: String, val nextKey: Int
)