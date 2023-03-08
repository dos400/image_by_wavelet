package uz.hamroev.imagebywavelet.fragment

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.mukesh.imageproccessing.OnProcessingCompletionListener
import com.mukesh.imageproccessing.PhotoFilter
import com.mukesh.imageproccessing.filters.AutoFix
import uz.hamroev.historyuz.utils.toast
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.constants.Constants
import uz.hamroev.imagebywavelet.databinding.FragmentResultBinding
import uz.hamroev.imagebywavelet.model.ImageFilter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

class ResultFragment : Fragment() {


    lateinit var binding: FragmentResultBinding

    private lateinit var photoFilter: PhotoFilter
    private lateinit var result: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater, container, false)


//        val imageFilter = arguments?.getSerializable("filter") as ImageFilter
//
//        binding.filterName.text = imageFilter.filterName



        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        //main code here
        /* will be this fragment
        1. select image from camera and gallery
        2. result image -> will save, share(this image), download to gallery
        */

        //bu narsani thread ga olib o'tishimiz kerak !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        photoFilter = PhotoFilter(binding.imageAfterFilter, object :
            OnProcessingCompletionListener {
            override fun onProcessingComplete(bitmap: Bitmap) {
                result = bitmap
            }
        })
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.filter_none)
        photoFilter.applyEffect(bitmap, AutoFix())

        binding.imageAfterFilter.setOnClickListener {
            saveImage()
        }




        binding.getImageFromGallery.setOnClickListener {
            ImagePicker
                .with(this)
                .cropSquare()
                .maxResultSize(1080, 1080)
                .galleryOnly()
                .createIntent {
                    startForProfileImageResult.launch(it)
                }



        }

        binding.getImageFromCamera.setOnClickListener {
            ImagePicker
                .with(this)
                .cropSquare()
                .maxResultSize(1080, 1080)
                .cameraOnly()
                .createIntent {
                    startForProfileImageResult.launch(it)
                }




        }


        return binding.root
    }

    override fun onPause() {
        super.onPause()
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!


                binding.imageDefault.setImageURI(fileUri)
                binding.imageDefault.tag = "img"

                var result:Bitmap
                photoFilter = PhotoFilter(binding.imageAfterFilter, object :
                    OnProcessingCompletionListener {
                    override fun onProcessingComplete(bitmap: Bitmap) {
                        result = bitmap
                    }
                })

                val bitmap = BitmapFactory.decodeFile(fileUri.path)
                photoFilter.applyEffect(bitmap, AutoFix())



            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                toast("${ImagePicker.getError(data)}")
            } else {
                toast("Task Cancelled")
            }
        }

    private fun saveImage() {
        val defaultPath = Environment.getExternalStorageDirectory().toString()
        val path = "${defaultPath}/${Constants.DIRECTORY_NAME}"
        File(path).mkdirs()
        val fOut: OutputStream?
        val fileName = Date().time
        val file = File(path, "$fileName.jpg")
        fOut = FileOutputStream(file)
        result.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
        fOut.flush()
        fOut.close()
        MediaStore.Images.Media.insertImage(
            activity?.contentResolver,
            file.absolutePath,
            file.name,
            file.name
        )
        Toast.makeText(requireContext(), "Image Saved", Toast.LENGTH_SHORT)
            .show()
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri ?: return@registerForActivityResult
            binding.imageDefault.setImageURI(uri)
            val ins = activity?.contentResolver?.openInputStream(uri)
            val file = File(activity?.filesDir, "imageNew.jpg")
            val fileOutputStream = FileOutputStream(file)
            ins?.copyTo(fileOutputStream)
            ins?.close()

            fileOutputStream.close()
            val absolutePath = file.absolutePath

            toast("file copied to ${absolutePath}")
        }

    private fun pickImageFromGallery() {
        getImageContent.launch("image/*")
    }
}