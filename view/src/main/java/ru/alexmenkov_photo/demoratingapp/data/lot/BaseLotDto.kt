package ru.alexmenkov_photo.demoratingapp.data.lot

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BaseLotDto(
    var id: Long? = null,
    var rating: Double? = null,
    var orderCount: Long? = null,
    var likeCount: Long? = null,
    var inStock: Boolean? = null,
    var lotCode: String? = null,
    var lotImageDtoList: List<LotImageDto> = emptyList(),
    var baseGroupDto: BaseGroupDto? = null
) : Parcelable
