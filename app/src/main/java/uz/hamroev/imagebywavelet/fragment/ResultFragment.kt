package uz.hamroev.imagebywavelet.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mukesh.imageproccessing.OnProcessingCompletionListener
import com.mukesh.imageproccessing.PhotoFilter
import com.mukesh.imageproccessing.filters.None
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.cache.Cache
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
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater, container, false)


        val imageFilter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("filter", ImageFilter::class.java)
        } else {
            TODO("VERSION.SDK_INT < TIRAMISU")
            arguments?.getSerializable("filter") as ImageFilter
        }
        binding.filterName.text = imageFilter?.filterName



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
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.eiffel)
        photoFilter.applyEffect(bitmap, imageFilter!!.filterFunctionName)

        binding.imageAfterFilter.setOnClickListener {
            saveImage()
        }




        return binding.root
    }

    override fun onPause() {
        super.onPause()

    }


    private fun saveImage() {
        val path = Environment.getExternalStorageDirectory()
            .toString()
        val fOut: OutputStream?
        val fileName = Date().time
        val file = File(path, "$fileName.jpg")
        fOut = FileOutputStream(file)
        result.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
        fOut.flush()
        fOut.close()
        MediaStore.Images.Media.insertImage(activity?.contentResolver, file.absolutePath, file.name, file.name)
        Toast.makeText(requireContext(), "ImageSaved", Toast.LENGTH_SHORT)
            .show()
    }

}