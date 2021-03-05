package ru.alexmenkov_photo.demoratingapp.data.lot

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LotImageDto(
    var id: Long?,
    var type: Int?,
    var url: String? = "no_image"
) : Parcelable