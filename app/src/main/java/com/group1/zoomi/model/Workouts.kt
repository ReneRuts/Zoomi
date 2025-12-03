package com.group1.zoomi.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Workouts(
    @StringRes val stringResourceID: Int,
    @DrawableRes val imageResourceId: Int = 0
)
