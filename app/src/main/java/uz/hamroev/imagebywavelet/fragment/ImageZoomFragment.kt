package uz.hamroev.imagebywavelet.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.hamroev.imagebywavelet.utils.serializable
import uz.hamroev.imagebywavelet.databinding.FragmentImageZoomBinding
import java.io.File


class ImageZoomFragment : Fragment() {


    lateinit var binding: FragmentImageZoomBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentImageZoomBinding.inflate(layoutInflater, container, false)


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        // this window can zoom any image
        val file = arguments?.serializable<File>("file")
        binding.image.setImageURI(Uri.fromFile(file))

        binding.shareButton.setOnClickListener {
            val bitmap = BitmapFactory.decodeFile(file?.path)
            onShare(bitmap)
        }
        

        return binding.root
    }

    private fun onShare(bitmap: Bitmap) {
        val path = MediaStore.Images.Media.insertImage(
            activity?.contentResolver,
            bitmap,
            "Image Descpreption",
            null
        )
        val uri = Uri.parse(path)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(shareIntent, "share image"))
    }


}