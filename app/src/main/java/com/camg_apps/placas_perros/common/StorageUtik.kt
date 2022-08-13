package com.camg_apps.placas_perros.common

import android.os.Build

inline fun <T> sdk29AndUp(ondSdk29: () -> T): T?{
return  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
    ondSdk29()
}else null
}