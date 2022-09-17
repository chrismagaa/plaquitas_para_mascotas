package com.camg_apps.placas_perros.ui.plantillas

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.camg_apps.placas_perros.R
import com.camg_apps.placas_perros.data.Template
import com.camg_apps.placas_perros.databinding.ActivityMainBinding.inflate
import com.camg_apps.placas_perros.databinding.FragmentPlantillaIFEBinding
import com.camg_apps.placas_perros.plantillas.plantillaIFEFragment
import com.camg_apps.placas_perros.plantillas.plantillaINEFragment


class plantillasAdapter(
    private val imagePhoto: Int,
    private val values: List<Template>,
    private val navController: NavController
) : RecyclerView.Adapter<plantillasAdapter.ViewHolder>() {

    var ctx: Context? = null;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ctx = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_plantillas, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
       // holder.ivDesign.setImageResource(item.drawableTemplateFrontal!!)
        val cvDeltantera = LayoutInflater.from(ctx).inflate(item.layoutFrontal ,null) as View

        cvDeltantera.findViewById<ImageView>(R.id.imageViewPet).setImageResource(imagePhoto)
        cvDeltantera.findViewById<ConstraintLayout>(R.id.contraint_background).setBackgroundResource(item.drawableTemplateFrontal!!)
        cvDeltantera.findViewById<TextView>(R.id.tvTitleCard).text = item.title
        cvDeltantera.findViewById<TextView>(R.id.tvSubtitleCard).text = item.subtitle


        holder.cvPlantilla.addView(cvDeltantera)
        holder.cvPlantilla.setOnClickListener {
            val bundle = bundleOf(plantillaIFEFragment.EXTRA_TEMPLATE to item,
            plantillaIFEFragment.EXTRA_IMAGE to imagePhoto)
            if(navController.currentDestination?.id == R.id.nav_plantillas){
                navController.navigate(R.id.action_nav_plantillas_to_plantillaIFEFragment, bundle)
            }else{
                navController.navigate(R.id.action_nav_plantillas_gatos_to_plantillaIFEFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       // val ivDesign: ImageView = view.findViewById(R.id.imageViewDesign)
        val cvPlantilla: ConstraintLayout = view.findViewById(R.id.cvPlantilla)

    }
}