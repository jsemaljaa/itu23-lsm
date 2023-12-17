// Author: Alina Vinogradova (xvinog00)

package com.itu.lsm.classes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Service(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val price: String = ""
) : Parcelable