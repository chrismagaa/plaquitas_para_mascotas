package com.camg_apps.placas_perros.ui.acerdade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.camg_apps.placas_perros.databinding.FragmentAcercaBinding

class AcercaDeFragment : Fragment() {

    private lateinit var acercaDeViewModel: AcercaDeViewModel
    private var _binding: FragmentAcercaBinding? = null
            val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAcercaBinding.inflate(inflater, container, false)
        acercaDeViewModel = ViewModelProvider(this).get(AcercaDeViewModel::class.java)

        acercaDeViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}