package com.camg_apps.placas_perros.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Template(
    var title: String?,
     var subtitle: String?,
     var leyenda: String?,
    val drawableTemplateFrontal: Int?,
    val drawableTemplateTrasera: Int?,
    val layoutFrontal: Int,
    val layoutTrasera: Int
): Parcelable
