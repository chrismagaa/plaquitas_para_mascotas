package com.camg_apps.placas_perros.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class Mascota(
        val nombre: String,
        val tel: String,
        val domicilio: String,
        val raza: String,
        val color: String,
        val ocupacion: String,
        val edad: String,
        val dueno: String,
        val sexo: String,
) : Parcelable {
}
