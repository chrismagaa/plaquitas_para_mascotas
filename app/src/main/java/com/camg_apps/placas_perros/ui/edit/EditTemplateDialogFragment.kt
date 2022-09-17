package com.camg_apps.placas_perros.ui.edit

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.camg_apps.placas_perros.R
import com.camg_apps.placas_perros.data.Mascota
import com.camg_apps.placas_perros.data.Template
import com.google.android.material.textfield.TextInputLayout

class EditTemplateDialogFragment(private var template: Template): DialogFragment() {


    private var mListener: EditTemplateInterface? = null
    interface EditTemplateInterface{
        fun changeData(dialog: DialogFragment, template: Template)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = layoutInflater
        val view: View = inflater.inflate(R.layout.fragment_edit_template, null)
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            setValues(view)

            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { _, _ ->
                    getValuesBoxes(view)
                    mListener!!.changeData(this, template)
                })
                .setNegativeButton("Cancelar",
                    DialogInterface.OnClickListener { _, _ ->
                        dialog?.dismiss()
                    })

            builder.setView(view)
            // Create the AlertDialog object and return it
            val dialogo = builder.create()
            dialogo
        }?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setValues(view: View) {
        if(template.title != null){
            view.findViewById<TextInputLayout>(R.id.tvTitle).editText!!.setText(template.title!!)
            view.findViewById<TextInputLayout>(R.id.tvTitle).visibility = View.VISIBLE
        }

        if(template.subtitle != null){
            view.findViewById<TextInputLayout>(R.id.tvSubtitulo).editText!!.setText(template.subtitle!!)
            view.findViewById<TextInputLayout>(R.id.tvSubtitulo).visibility = View.VISIBLE
        }

        if(template.leyenda != null ){
            view.findViewById<TextInputLayout>(R.id.tvLeyenda).editText!!.setText(template.leyenda!!)
            view.findViewById<TextInputLayout>(R.id.tvLeyenda).visibility = View.VISIBLE
        }

    }

    private fun getValuesBoxes(view: View) {
        val titulo = view.findViewById<TextInputLayout>(R.id.tvTitle).editText!!.text.toString()
        val subtitulo = view.findViewById<TextInputLayout>(R.id.tvSubtitulo).editText!!.text.toString()
        val leyenda = view.findViewById<TextInputLayout>(R.id.tvLeyenda).editText!!.text.toString()


        template.title = titulo
        template.subtitle = subtitulo
        template.leyenda = leyenda

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mListener = parentFragment as EditTemplateInterface

        }catch (e: ClassCastException){
            Log.e(ContentValues.TAG, "onAttach: " + e.message);
        }
    }
}