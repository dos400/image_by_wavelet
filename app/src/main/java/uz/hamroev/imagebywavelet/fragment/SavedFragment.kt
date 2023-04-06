package uz.hamroev.imagebywavelet.fragment

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.hamroev.imagebywavelet.utils.gone
import uz.hamroev.imagebywavelet.utils.visible
import uz.hamroev.imagebywavelet.adapters.GalleryAdapter
import uz.hamroev.imagebywavelet.cache.Cache
import uz.hamroev.imagebywavelet.constants.Constants
import uz.hamroev.imagebywavelet.databinding.FragmentSavedBinding
import java.io.File


class SavedFragment : Fragment() {

    private lateinit var images: List<File>
    private lateinit var galleryAdapter: GalleryAdapter

    lateinit var binding: FragmentSavedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSavedBinding.inflate(layoutInflater, container, false)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        /*

       here will saved images

        * */
        loadImages()
        if (images.isNotEmpty()) {
            binding.apply {
                one.gone()
                two.visible()
            }
            Log.d("TTTT", "onCreateView: ${images}\n")
            galleryAdapter = GalleryAdapter(
                requireContext(),
                images.reversed(),
                object : GalleryAdapter.OnSavedImageClickListener {
                    override fun onClick(file: File) {
                        val uri = Uri.fromFile(file)
                        Cache.imageFile = uri.toString()

                        val bundle = Bundle()
                        bundle.putSerializable("file", file)

                        findNavController().navigate(
                            uz.hamroev.imagebywavelet.R.id.imageZoomFragment,
                            bundle
                        )
                    }
                })
            binding.rvSavedImage.adapter = galleryAdapter
        } else {
            binding.apply {
                one.visible()
                two.gone()
            }

        }




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