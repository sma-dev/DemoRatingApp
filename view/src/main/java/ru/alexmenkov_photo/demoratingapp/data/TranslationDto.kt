package ru.alexmenkov_photo.demoratingapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TranslationDto(
    var langCode: String?,
    var value: String?
) : Parcelable