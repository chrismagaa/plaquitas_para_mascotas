package com.camg_apps.placas_perros.data

import android.content.Context
import android.content.res.Resources
import com.camg_apps.placas_perros.R

class ProviderData(private val context: Context) {

         val templatesPerros: ArrayList<Template> = arrayListOf(
            Template(context.getString(R.string.txt_instituto_federal_canino),
                context.getString(R.string.txt_credencial_ladrar),
                context.getString(R.string.reverse_plaquita_text), R.drawable.ic_nuevo_fondo_ine,R.drawable.ic_fondo_atras_ine, R.layout.template_ine_frontal, R.layout.template_ine_trasera),
             Template(context.getString(R.string.txt_instituto_federal_canino),
                 context.getString(R.string.txt_credencial_ladrar),
                 context.getString(R.string.reverse_plaquita_text),R.drawable.ic_fondo_nuevo_ife,R.drawable.ic_fondo_detras_ife, R.layout.template_ife_frontal, R.layout.template_ife_trasera),
             Template(context.getString(R.string.txt_instituto_federal_canino),
                 context.getString(R.string.txt_credencial_ladrar),
                 context.getString(R.string.reverse_plaquita_text),R.drawable.ic_fondo_ife_azulito,R.drawable.ic_fondo_detras_ife_azul, R.layout.template_ife_frontal, R.layout.template_ife_trasera),

             )


        val templatesGatos: ArrayList<Template> = arrayListOf(
            Template(context.getString(R.string.txt_instituto_federal_gatuno),
                    context.getString(R.string.txt_credencial_maullar),
                context.getString(R.string.reverse_plaquita_text), R.drawable.ic_nuevo_fondo_ine,R.drawable.ic_fondo_atras_ine, R.layout.template_ine_frontal, R.layout.template_ine_trasera),
            Template(context.getString(R.string.txt_instituto_federal_gatuno),
                context.getString(R.string.txt_credencial_maullar),
                context.getString(R.string.reverse_plaquita_text),R.drawable.ic_fondo_nuevo_ife,R.drawable.ic_fondo_detras_ife, R.layout.template_ife_frontal, R.layout.template_ife_trasera),
            Template(context.getString(R.string.txt_instituto_federal_gatuno),
                context.getString(R.string.txt_credencial_maullar),
                context.getString(R.string.reverse_plaquita_text),R.drawable.ic_fondo_ife_azulito,R.drawable.ic_fondo_detras_ife_azul, R.layout.template_ife_frontal, R.layout.template_ife_trasera),

            )
}