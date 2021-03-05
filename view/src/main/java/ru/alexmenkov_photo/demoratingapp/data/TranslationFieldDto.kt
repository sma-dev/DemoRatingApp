package ru.alexmenkov_photo.demoratingapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TranslationFieldDto(
    val id: Long?,
    val requestedTranslation: TranslationDto?,
    val originTranslation: TranslationDto?
) : Parcelable