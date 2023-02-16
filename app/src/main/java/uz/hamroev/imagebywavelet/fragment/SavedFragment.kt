package uz.hamroev.imagebywavelet.fragment

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.hamroev.imagebywavelet.constants.Constants
import uz.hamroev.imagebywavelet.databinding.FragmentSavedBinding
import java.io.File


class SavedFragment : Fragment() {

    private lateinit var images: List<File>


    lateinit var binding: FragmentSavedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(layoutInflater, container, false)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        /*

       here will saved images

        * */

        loadImages()
//        Log.d("TTTT", "onCreateView: ${images}")

        return binding.root
    }

    private fun loadImages() {
        var path = Environment.getExternalStorageDirectory().toString()
        path += "/${Constants.DIRECTORY_NAME}"
        val directory = File(path)
        val files = directory.listFiles()
        images = files?.filter {
            it.path.endsWith(".jpg")
        } ?: emptyList()
    }


}