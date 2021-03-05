package ru.alexmenkov_photo.demoratingapp.service.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "")
class Lot(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lot_id")
    var id: Long,
    @ColumnInfo(name = "lot_code")
    var lotCode: String? = "",
    @ColumnInfo(name = "lot_title")
    var lotTitle: String? = "",
    @ColumnInfo(name = "lot_description")
    var lotDescription: String? = "",
    @ColumnInfo(name = "image_type")
    var imageType: Int? = 0,
    @ColumnInfo(name = "image_url")
    var imageUrl: String? = "no_image",
    @ColumnInfo(name = "rating")
    val rating: Double = 0.0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Lot

        if (id != other.id) return false
        if (lotCode != other.lotCode) return false
        if (lotTitle != other.lotTitle) return false
        if (lotDescription != other.lotDescription) return false
        if (imageType != other.imageType) return false
        if (imageUrl != other.imageUrl) return false
        if (rating != other.rating) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (lotCode?.hashCode() ?: 0)
        result = 31 * result + (lotTitle?.hashCode() ?: 0)
        result = 31 * result + (lotDescription?.hashCode() ?: 0)
        result = 31 * result + (imageType ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + rating.hashCode()
        return result
    }
}