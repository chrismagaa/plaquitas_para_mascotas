package com.camg_apps.placas_perros.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Template(
    private var title: Int?,
    private var subtitle: Int?,
    private var leyenda: Int?,
    val drawableTemplateFrontal: Int?,
    val drawableTemplateTrasera: Int?,
    val layoutFrontal: Int,
    val layoutTrasera: Int
): Parcelable
