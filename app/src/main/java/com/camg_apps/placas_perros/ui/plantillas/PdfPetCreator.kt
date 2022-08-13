package com.camg_apps.placas_perros.ui.plantillas

import android.graphics.Bitmap
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import java.io.ByteArrayOutputStream
import java.io.File

class PdfPetCreator (var imagePlaca: Bitmap){

    fun createPdf(file: File){
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        pdfDocument.defaultPageSize = PageSize.LETTER
        val document = Document(pdfDocument)
        document.setMargins(20F, 40F, 10F, 40F)
        val stream = ByteArrayOutputStream()
        imagePlaca.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val bitmapData = stream.toByteArray()
        val imageData = ImageDataFactory.create(bitmapData)
        val image = Image(imageData)
        image.setHeight(140F)
        image.setWidth(100F)

        document.add(image)
        document.close()

    }

}