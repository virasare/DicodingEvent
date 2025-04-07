package com.dicoding.dicodingevent.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: String,
    val name: String,
    val imageLogo: String,
    val isFavorite: Boolean
): Parcelable