package com.camg_apps.placas_perros.plantillas

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.camg_apps.placas_perros.R
import com.camg_apps.placas_perros.admob.AdMobManager
import com.camg_apps.placas_perros.common.sdk29AndUp
import com.camg_apps.placas_perros.data.Mascota
import com.camg_apps.placas_perros.databinding.FragmentPlantillaINEBinding
import com.camg_apps.placas_perros.ui.edit.EditFragment
import com.camg_apps.placas_perros.ui.plantillas.PdfPetCreator
import com.camg_apps.placas_perros.ui.preview.PdfPreviewFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.ads.AdRequest
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


class plantillaINEFragment : Fragment(), EditFragment.EditDialogInterface {

    private var mPetImageUri: Uri? = null
    private var _binding: FragmentPlantillaINEBinding? = null
    private val binding get() = _binding!!
    private var mascota: Mascota? = null

    private var readPermissionGranted = false
    private var writePermissionGranted = false

    private lateinit var permissionLaucher: ActivityResultLauncher<Array<String>>

    companion object {
        const val EXTRA_BACKGROUND = "background_placa"
        const val EXTRA_BACKGROUND_ATRAS = "background_placa_atras"
    }

    var background by Delegates.notNull<Int>()
    var background_detras by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            background = it.getInt(EXTRA_BACKGROUND)
            background_detras = it.getInt(EXTRA_BACKGROUND_ATRAS)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantillaINEBinding.inflate(inflater, container, false)

        binding.clFrente.setBackgroundResource(background)
        binding.clTraseraINE.setBackgroundResource(background_detras)

        AdMobManager.loadAd(requireActivity())

        setHasOptionsMenu(true)
        setDataViews()
        initButtonColor()

        binding.btnImagen.setOnClickListener {
            getPetImage()
        }
        binding.btnEdit.setOnClickListener {
            showDialogEditTextos()
        }
        binding.btnRotar.setOnClickListener {
            rotarPlaca()
        }

        permissionLaucher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                readPermissionGranted =
                    permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: readPermissionGranted
                writePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                    ?: writePermissionGranted

            }
        updateOrRequestPermissions()

        return binding.root
    }

    private fun rotarPlaca() {
        if(binding.cardViewDelantera.isVisible){
            binding.cardViewDelantera.visibility = View.INVISIBLE
            binding.cardViewTrasera.visibility  = View.VISIBLE
        }else{
            binding.cardViewDelantera.visibility = View.VISIBLE
            binding.cardViewTrasera.visibility  = View.INVISIBLE
        }
    }

    private fun showDialogEditTextos() {
        val dialog = EditFragment(mascota ?: Mascota(
            binding.tvNombre.text.toString(),
            binding.tvTel.text.toString(),
            binding.tvDomicilio.text.toString(),
            binding.tvRaza.text.toString(),
            binding.tvColor.text.toString(),
            binding.tvOcupacion.text.toString(),
            binding.tvEdad.text.toString(),
            binding.editNombreDueno.text.toString(),
            ""))
        dialog.show(childFragmentManager, "SHOW_EDIT_DIALOG")
    }


    private fun updateOrRequestPermissions() {
        val hasReadPersmission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val hasWritePersmission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPersmission
        writePermissionGranted = hasWritePersmission || minSdk29

        var permissionsToRequest = mutableListOf<String>()
        if (!writePermissionGranted) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!readPermissionGranted) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionsToRequest.isNotEmpty()) {
            permissionLaucher.launch(permissionsToRequest.toTypedArray())
        }
    }


    fun crearPdf() {
        val bsPlaca = ByteArrayOutputStream()
        val bitmapPlaca = getBitmapFromView(binding.constraintLayoutPlaca)
        bitmapPlaca?.compress(Bitmap.CompressFormat.PNG, 50, bsPlaca)

        val calendarIns = Calendar.getInstance().time
        val now = SimpleDateFormat("dd-MM-yyyy").format(calendarIns)

        val outputDir = requireContext().cacheDir

        var outputFile: File? = null
        outputFile = File.createTempFile(now, ".pdf", outputDir)
        outputFile.deleteOnExit()
        val pdfCreator = PdfPetCreator(bitmapPlaca!!)
        pdfCreator.createPdf(outputFile)


        val bundle = bundleOf(PdfPreviewFragment.ARG_ABSULTE_PATH to outputFile!!.absolutePath)
        findNavController().navigate(
            R.id.action_plantillaINEFragment_to_pdfPreviewFragment,
            bundle
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_plantilla, menu)
    }

    private fun savePhotoToExternalStorage(displayName: String, bmp: Bitmap): Boolean {
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.WIDTH, bmp.width)
            put(MediaStore.Images.Media.HEIGHT, bmp.height)
        }

        return try {
            requireContext().contentResolver.insert(imageCollection, contentValues)?.also { uri ->
                requireContext().contentResolver.openOutputStream(uri).use { outputStream ->
                    if (!bmp.compress(Bitmap.CompressFormat.PNG, 95, outputStream)) {
                        throw  IOException("Couldnt save bitmap")
                    } else {
                        Toast.makeText(requireContext(), "Guardado en galeria: "+uri.path, Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: throw IOException("Couldn't create MediaStore entry")
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_compartir -> {
                showDialogGuardarTarjeta("Compartir", false)
            }
            R.id.action_download -> {
                showDialogGuardarTarjeta("Guardar", true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialogGuardarTarjeta(text: String, isGuardar: Boolean) {
        val items = arrayOf("Parte delantera", "Parte trasera")
        var selectedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(text)
            .setSingleChoiceItems(items, 0) { dialogInterface: DialogInterface, i: Int ->
                selectedItem = i
            }
            .setPositiveButton(R.string.txt_aceptar) { _, _ ->
                if (selectedItem == 0) {
                    if (isGuardar) {
                        guardarParteDelantera()
                    } else {
                        compartirParteDelantera()
                    }
                } else {
                    if (isGuardar) {
                        guardarParteTrasera()
                    } else {
                        compartirParteTrasera()
                    }
                }
            }
            .show()
    }


    private fun shareImage(displayName: String, bmp: Bitmap): Boolean {
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI


        val contetValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.WIDTH, bmp.width)
            put(MediaStore.Images.Media.HEIGHT, bmp.height)
        }

        return try {
            requireContext().contentResolver.insert(imageCollection, contetValues)?.also { uri ->
                requireContext().contentResolver.openOutputStream(uri).use { outputStream ->
                    if (!bmp.compress(Bitmap.CompressFormat.PNG, 95, outputStream)) {
                        throw IOException("Couldn't save bitmap")
                    } else {
                        val shareIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_STREAM, uri)
                            type = "image/png"
                        }
                        startActivity(
                            Intent.createChooser(
                                shareIntent,
                                resources.getText(R.string.send_to)
                            )
                        )
                    }
                }
            } ?: throw IOException("Couldn't create MediStore entry")
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun compartirParteTrasera() {
        if (writePermissionGranted) {
            AdMobManager.showAd(requireActivity()) {
                shareImage(
                    UUID.randomUUID().toString(),
                    getBitmapFromView(binding.cardViewTrasera)!!
                )

            }
        } else {
            updateOrRequestPermissions()
        }
    }

    private fun compartirParteDelantera() {
        if (writePermissionGranted) {
            AdMobManager.showAd(requireActivity()) {
                shareImage(
                    UUID.randomUUID().toString(),
                    getBitmapFromView(binding.cardViewDelantera)!!
                )
            }
        } else {
            updateOrRequestPermissions()
        }
    }

    private fun guardarParteTrasera() {
        if (writePermissionGranted) {
            AdMobManager.showAd(requireActivity()){
                savePhotoToExternalStorage(
                    UUID.randomUUID().toString(),
                    getBitmapFromView(binding.cardViewTrasera)!!
                )
            }

        } else {
            updateOrRequestPermissions()
        }
    }


    private fun guardarParteDelantera() {
        if (writePermissionGranted) {
            AdMobManager.showAd(requireActivity()) {
                savePhotoToExternalStorage(
                    UUID.randomUUID().toString(),
                    getBitmapFromView(binding.cardViewDelantera)!!
                )
            }
        } else {
            updateOrRequestPermissions()
        }
    }


    private fun showDialogSizePdf() {
        val items = arrayOf("Plaquita", "Identificación")
        var selectedItem = 0

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Selecciona un tamaño")
            .setSingleChoiceItems(items, 0) { dialogInterface: DialogInterface, i: Int ->
                selectedItem = i
            }
            .show()
    }

    private fun getPetImage() {
        ImagePicker.with(this)
            .crop(
                3f,
                4f
            )                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent {
                startForProfileImageResult.launch(it)
            }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                mPetImageUri = fileUri
                mPetImageUri?.let {
                    binding.imageViewPet.setImageURI(it)
                }
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    private fun initButtonColor() {
        /*
        binding.btnColor.setOnClickListener {
            ColorPickerDialogBuilder
                .with(context)
                .setTitle("Choose color")
                .initialColor(R.color.black)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton(
                    "ok"
                ) { dialog, selectedColor, allColors -> changeBackgroundColor(selectedColor) }
                .build()
                .show()
        }

         */
    }

    private fun changeBackgroundColor(selectedColor: Int) {
        binding.clFrente.background.setTint(selectedColor)
        binding.clTraseraINE.background.setTint(selectedColor)
    }


    @SuppressLint("SetTextI18n")
    private fun setDataViews() {
        mascota?.let { mascota ->

            binding.tvNombre.text = mascota.nombre
            binding.tvTel.text = mascota.tel
            binding.tvDomicilio.text = mascota.domicilio
            binding.tvRaza.text = mascota.raza
            binding.tvColor.text = mascota.color
            binding.tvOcupacion.text = mascota.ocupacion
            binding.tvEdad.text = mascota.edad
            //binding.editSexo.text = mascota.sexo
            binding.editNombreDueno.text = mascota.dueno
            mPetImageUri?.let {
                binding.imageViewPet.setImageURI(it)
            }
        }
    }

    fun getBitmapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun changeData(dialog: DialogFragment, mascotaNuevo: Mascota) {
        this.mascota = mascotaNuevo
        setDataViews()
        dialog.dismiss()
    }


}