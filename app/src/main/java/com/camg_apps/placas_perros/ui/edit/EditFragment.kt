package com.camg_apps.placas_perros.ui.edit

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.camg_apps.placas_perros.R
import com.camg_apps.placas_perros.data.Mascota
import com.google.android.material.textfield.TextInputLayout



class EditFragment(private var mascota: Mascota) : DialogFragment() {

    private var mListener: EditDialogInterface? = null
    interface EditDialogInterface{
        fun changeData(dialog: DialogFragment, mascota: Mascota)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater: LayoutInflater = layoutInflater
        val view: View = inflater.inflate(R.layout.fragment_edit, null)
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            setValues(view)

            builder.setPositiveButton("Aceptar",
                DialogInterface.OnClickListener { _, _ ->
                        getValuesBoxes(view)
                    mListener!!.changeData(this, mascota)
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
        view.findViewById<TextInputLayout>(R.id.textInputNombre).editText!!.setText(mascota.nombre)
        view.findViewById<TextInputLayout>(R.id.textInputTelefono).editText!!.setText(mascota.tel)
        view.findViewById<TextInputLayout>(R.id.textInputDomicilio).editText!!.setText(mascota.domicilio)
        view.findViewById<TextInputLayout>(R.id.textInputRaza).editText!!.setText(mascota.raza)
        view.findViewById<TextInputLayout>(R.id.textInputColor).editText!!.setText(mascota.color)
        view.findViewById<TextInputLayout>(R.id.textInputOcupacion).editText!!.setText(mascota.ocupacion)
        view.findViewById<TextInputLayout>(R.id.textInputEdad).editText!!.setText(mascota.edad)
        view.findViewById<TextInputLayout>(R.id.textInputNombreDueno).editText!!.setText(mascota.dueno)
    }
/*
    private fun goToPreview() {
        val bundle = bundleOf(plantillaINEFragment.EXTRA_MASCOTA to mascota)
        findNavController().navigate(R.id.action_editFragment_to_plantillaINEFragment, bundle)
    }

 */
    private fun getValuesBoxes(view: View) {
        val nombre = view.findViewById<TextInputLayout>(R.id.textInputNombre).editText!!.text.toString()
        val telefono = view.findViewById<TextInputLayout>(R.id.textInputTelefono).editText!!.text.toString()
        val domicilio = view.findViewById<TextInputLayout>(R.id.textInputDomicilio).editText!!.text.toString()
        val raza = view.findViewById<TextInputLayout>(R.id.textInputRaza).editText!!.text.toString()
        val color = view.findViewById<TextInputLayout>(R.id.textInputColor).editText!!.text.toString()
        val ocupacion = view.findViewById<TextInputLayout>(R.id.textInputOcupacion).editText!!.text.toString()
        val edad = view.findViewById<TextInputLayout>(R.id.textInputEdad).editText!!.text.toString()
        val nombreDueno = view.findViewById<TextInputLayout>(R.id.textInputNombreDueno).editText!!.text.toString()


        mascota = Mascota(
            nombre,
            telefono,
            domicilio,
            raza,
            color,
            ocupacion,
            edad,
            nombreDueno
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            mListener = parentFragment as EditDialogInterface

        }catch (e: ClassCastException){
            Log.e(ContentValues.TAG, "onAttach: " + e.message);
        }
    }
}