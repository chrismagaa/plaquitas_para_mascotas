package com.camg_apps.placas_perros.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View

class MyUtil {
    companion object {
        fun setVisibility(isVisible: Boolean): Int {
            if (isVisible) return View.VISIBLE
            return View.GONE
        }

        fun valorarApp(activity: Activity, url: String = "https://play.google.com/store/apps/details?id=com.camg_apps.plaquitas_mascotas") {
            val webpage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            activity.startActivity(intent)
        }

        fun compartirApp(act: Activity, ctx: Context) {
            var urlApp = "https://play.google.com/store/apps/details?id=com.camg_apps.plaquitas_mascotas"
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, urlApp)
            }
            if (intent.resolveActivity(act.packageManager) != null) {
                ctx.startActivity(intent)
            }
        }


    }
    }