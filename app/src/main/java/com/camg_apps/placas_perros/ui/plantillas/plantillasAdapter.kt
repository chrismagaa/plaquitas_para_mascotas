package com.camg_apps.placas_perros.ui.plantillas

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.camg_apps.placas_perros.R
import com.camg_apps.placas_perros.plantillas.plantillaIFEFragment
import com.camg_apps.placas_perros.plantillas.plantillaINEFragment


class plantillasAdapter(
    private val values: List<Int>,
    private val navController: NavController
) : RecyclerView.Adapter<plantillasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_plantillas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.ivDesign.setImageResource(item)
        holder.cvPlantilla.setOnClickListener {
            when(position){
                0 -> {
                    val bundle = bundleOf(plantillaINEFragment.EXTRA_BACKGROUND to R.drawable.ic_nuevo_fondo_ine,
                        plantillaINEFragment.EXTRA_BACKGROUND_ATRAS to R.drawable.ic_fondo_atras_ine)
                    navController.navigate(R.id.action_nav_plantillas_to_plantillaINEFragment, bundle)
                }
                1 -> {
                    val bundle = bundleOf(plantillaIFEFragment.EXTRA_BACKGROUND to R.drawable.ic_fondo_nuevo_ife,
                        plantillaIFEFragment.EXTRA_BACKGROUND_ATRAS to R.drawable.ic_fondo_detras_ife)
                    navController.navigate(R.id.action_nav_plantillas_to_plantillaIFEFragment, bundle)
                }
                2 -> {
                    val bundle = bundleOf(plantillaIFEFragment.EXTRA_BACKGROUND to R.drawable.ic_fondo_ife_azulito,
                        plantillaIFEFragment.EXTRA_BACKGROUND_ATRAS to R.drawable.ic_fondo_detras_ife_azul)
                    navController.navigate(R.id.action_nav_plantillas_to_plantillaIFEFragment, bundle)
                }

            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivDesign: ImageView = view.findViewById(R.id.imageViewDesign)
        val cvPlantilla: ConstraintLayout = view.findViewById(R.id.cvPlantilla)

    }
}