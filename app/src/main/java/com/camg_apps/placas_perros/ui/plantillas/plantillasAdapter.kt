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
import com.camg_apps.placas_perros.data.Template
import com.camg_apps.placas_perros.databinding.FragmentPlantillaIFEBinding
import com.camg_apps.placas_perros.plantillas.plantillaIFEFragment
import com.camg_apps.placas_perros.plantillas.plantillaINEFragment


class plantillasAdapter(
    private val values: List<Template>,
    private val navController: NavController
) : RecyclerView.Adapter<plantillasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_plantillas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.ivDesign.setImageResource(item.drawableTemplateFrontal!!)
        holder.cvPlantilla.setOnClickListener {
            val bundle = bundleOf(plantillaIFEFragment.EXTRA_TEMPLATE to item)
            navController.navigate(R.id.action_nav_plantillas_to_plantillaIFEFragment, bundle)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivDesign: ImageView = view.findViewById(R.id.imageViewDesign)
        val cvPlantilla: ConstraintLayout = view.findViewById(R.id.cvPlantilla)

    }
}