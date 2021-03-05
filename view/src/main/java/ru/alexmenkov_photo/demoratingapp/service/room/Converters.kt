package ru.alexmenkov_photo.demoratingapp.service.room

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toBigDecimal(string: String?): BigDecimal {
        return BigDecimal(string)
    }

    @TypeConverter
    fun fromBigDecimal(bigDecimal: BigDecimal): String {
        return bigDecimal.toString()
    }
}