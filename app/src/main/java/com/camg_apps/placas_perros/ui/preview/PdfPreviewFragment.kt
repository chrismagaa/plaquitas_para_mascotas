package com.camg_apps.placas_perros.ui.preview

import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.camg_apps.placas_perros.databinding.FragmentPdfPreviewBinding
import com.github.barteksc.pdfviewer.listener.OnDrawListener
import java.io.File



class PdfPreviewFragment : Fragment(), OnDrawListener {
    private var path: String? = null

    companion object{
         const val ARG_ABSULTE_PATH = "path_pdf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            path = it.getString(ARG_ABSULTE_PATH)
        }
    }

    private var _binding: FragmentPdfPreviewBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPdfPreviewBinding.inflate(inflater, container, false)

        Toast.makeText(requireContext(), path, Toast.LENGTH_SHORT).show()
        loadFile()

        return binding.root
    }


    fun loadFile(){
        binding.pdfPreView.fromFile(File(path)).
        spacing(0)
            .enableAnnotationRendering(false)
            .scrollHandle(null)
            .onDraw(this)
            .load()
    }

    override fun onLayerDrawn(
        canvas: Canvas?,
        pageWidth: Float,
        pageHeight: Float,
        displayedPage: Int
    ) {
        var paint = Paint()
        canvas?.drawLine(0F, 0F, pageWidth, 0F, paint)
        canvas?.drawLine(0F, pageHeight, pageWidth, pageHeight, paint)
        canvas?.drawLine(0F, 0F, 0F, pageHeight, paint)
        canvas?.drawLine(pageWidth, 0F, pageWidth, pageHeight, paint)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}