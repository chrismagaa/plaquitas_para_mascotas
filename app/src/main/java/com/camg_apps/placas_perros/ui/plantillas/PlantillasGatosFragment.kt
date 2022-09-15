package com.camg_apps.placas_perros.ui.plantillas

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.camg_apps.placas_perros.R
import com.camg_apps.placas_perros.common.MyUtil
import com.camg_apps.placas_perros.data.providerData
import com.camg_apps.placas_perros.databinding.FragmentPlantillasGatosListBinding
import com.camg_apps.placas_perros.databinding.FragmentPlantillasListBinding

class PlantillasGatosFragment: Fragment() {


    private var _binding: FragmentPlantillasGatosListBinding? = null
    val binding get () = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentPlantillasGatosListBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = plantillasAdapter(providerData.templatesGatos, findNavController())


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_compartir -> {
                compartirApp()
            }
            R.id.action_valorar -> {
                valorarApp()
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun compartirApp() {
        MyUtil.compartirApp(requireActivity(), requireContext())
    }

    private fun valorarApp() {
        MyUtil.valorarApp(requireActivity())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}