package ru.alexmenkov_photo.demoratingapp.data.lot

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.alexmenkov_photo.demoratingapp.data.TranslationFieldDto

@Parcelize
data class BaseGroupDto(
    var id: Long?,
    var title: TranslationFieldDto?,
    var description: TranslationFieldDto?,
) : Parcelable