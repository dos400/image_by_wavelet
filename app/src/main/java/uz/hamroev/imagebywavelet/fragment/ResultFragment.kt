package uz.hamroev.imagebywavelet.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.mukesh.imageproccessing.OnProcessingCompletionListener
import com.mukesh.imageproccessing.PhotoFilter
import com.mukesh.imageproccessing.filters.*
import uz.hamroev.imagebywavelet.utils.toast
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.cache.Cache
import uz.hamroev.imagebywavelet.constants.Constants
import uz.hamroev.imagebywavelet.databinding.FragmentResultBinding
import uz.hamroev.imagebywavelet.databinding.InfoDialogBinding
import java.io.*
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
        Cache.init(requireContext())


        // set filter name to top bar
        binding.filterName.text = Cache.filterName.toString().trim()

        // click back button
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
        when (Cache.filterName) {
            "None" -> {
                photoFilter.applyEffect(bitmap, None())
            }
            "AutoFix" -> {
                photoFilter.applyEffect(bitmap, AutoFix())
            }
            "Brightness" -> {
                photoFilter.applyEffect(bitmap, Brightness())
            }
            "Contrast" -> {
                photoFilter.applyEffect(bitmap, Contrast())
            }
            "Temperature" -> {
                photoFilter.applyEffect(bitmap, Temperature())
            }
            "Grayscale" -> {
                photoFilter.applyEffect(bitmap, Grayscale())
            }
            "CrossProcess" -> {
                photoFilter.applyEffect(bitmap, CrossProcess())
            }
            "Documentary" -> {
                photoFilter.applyEffect(bitmap, Documentary())
            }
            "DuoTone" -> {
                photoFilter.applyEffect(bitmap, DuoTone())
            }
            "Tint" -> {
                photoFilter.applyEffect(bitmap, Tint())
            }
            "Fill-Light" -> {
                photoFilter.applyEffect(bitmap, FillLight())
            }
            "FishEye" -> {
                photoFilter.applyEffect(bitmap, FishEye())
            }
            "Grain" -> {
                photoFilter.applyEffect(bitmap, Grain())
            }
            "Highlight" -> {
                photoFilter.applyEffect(bitmap, Highlight())
            }
            "Lomoish" -> {
                photoFilter.applyEffect(bitmap, Lomoish())
            }
            "Flip Horizontally" -> {
                photoFilter.applyEffect(bitmap, FlipHorizontally())
            }
            "Flip Vertically" -> {
                photoFilter.applyEffect(bitmap, FlipVertically())
            }
            "Negative" -> {
                photoFilter.applyEffect(bitmap, Negative())
            }
            "Posterize" -> {
                photoFilter.applyEffect(bitmap, Posterize())
            }
            "Rotate" -> {
                photoFilter.applyEffect(bitmap, Rotate())
            }
            "Saturate" -> {
                photoFilter.applyEffect(bitmap, Saturate())
            }
            "Sepia" -> {
                photoFilter.applyEffect(bitmap, Sepia())
            }
            "Sharpen" -> {
                photoFilter.applyEffect(bitmap, Sharpen())
            }
            "Vignette" -> {
                photoFilter.applyEffect(bitmap, Vignette())
            }
        }



        binding.getImageFromGallery.setOnClickListener {
            ImagePicker
                .with(this)
                .cropSquare()
                .maxResultSize(1080, 1080)
                .galleryOnly()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        binding.getImageFromCamera.setOnClickListener {
            ImagePicker
                .with(this)
                .cropSquare()
                .maxResultSize(1080, 1080)
                .cameraOnly()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.apply {
            cardSaveButton.setOnClickListener { onSave() }
            cardShareButton.setOnClickListener { onShare() }
            cardDownloadButton.setOnClickListener { onDownload() }
        }

        binding.infoButton.setOnClickListener {

            val alertDialog = android.app.AlertDialog.Builder(binding.root.context)
            val dialog = alertDialog.create()
            val bindingInfo =
                InfoDialogBinding.inflate(LayoutInflater.from(requireContext()))
            dialog.setView(bindingInfo.root)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)



            when (Cache.filterName) {
                "None" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.none)}"
                }
                "AutoFix" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.autoFix)}"
                }
                "Brightness" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.brightness)}"
                }
                "Contrast" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.contrast)}"
                }
                "Temperature" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.temperature)}"
                }
                "Grayscale" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.grayscale)}"
                }
                "CrossProcess" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.crossProcess)}"
                }
                "Documentary" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.documentary)}"
                }
                "DuoTone" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.duoTone)}"
                }
                "Tint" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.tint)}"
                }
                "Fill-Light" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.fill_light)}"
                }
                "FishEye" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.fishEye)}"
                }
                "Grain" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.grain)}"
                }
                "Highlight" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.highlight)}"
                }
                "Lomoish" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.lomoish)}"
                }
                "Flip Horizontally" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.flip_horizontal)}"
                }
                "Flip Vertically" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.flip_vertical)}"
                }
                "Negative" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.negative)}"
                }
                "Posterize" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.posterize)}"
                }
                "Rotate" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.rotate)}"
                }
                "Saturate" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.saturate)}"
                }
                "Sepia" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.sepia)}"
                }
                "Sharpen" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.sharpen)}"
                }
                "Vignette" -> {
                    bindingInfo.infoText.text = "${activity?.resources?.getString(R.string.vignette)}"
                }
            }

            dialog.show()

        }





        return binding.root
    }

    private fun onSave() {

    }

    private fun onShare() {
        val path = MediaStore.Images.Media.insertImage(
            activity?.contentResolver,
            result,
            "Image Descpreption",
            null
        )
        val uri = Uri.parse(path)
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "image/*"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "nimadir texti bo'ladi bu yerda")
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(shareIntent, "share image"))
    }

    private fun onDownload() {
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


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!


                binding.imageDefault.setImageURI(fileUri)

                val bitmap = BitmapFactory.decodeFile(fileUri.path)

                when (Cache.filterName) {
                    "None" -> {
                        photoFilter.applyEffect(bitmap, None())
                    }
                    "AutoFix" -> {
                        photoFilter.applyEffect(bitmap, AutoFix())
                    }
                    "Brightness" -> {
                        photoFilter.applyEffect(bitmap, Brightness())
                    }
                    "Contrast" -> {
                        photoFilter.applyEffect(bitmap, Contrast())
                    }
                    "Temperature" -> {
                        photoFilter.applyEffect(bitmap, Temperature())
                    }
                    "Grayscale" -> {
                        photoFilter.applyEffect(bitmap, Grayscale())
                    }
                    "CrossProcess" -> {
                        photoFilter.applyEffect(bitmap, CrossProcess())
                    }
                    "Documentary" -> {
                        photoFilter.applyEffect(bitmap, Documentary())
                    }
                    "DuoTone" -> {
                        photoFilter.applyEffect(bitmap, DuoTone())
                    }
                    "Tint" -> {
                        photoFilter.applyEffect(bitmap, Tint())
                    }
                    "Fill-Light" -> {
                        photoFilter.applyEffect(bitmap, FillLight())
                    }
                    "FishEye" -> {
                        photoFilter.applyEffect(bitmap, FishEye())
                    }
                    "Grain" -> {
                        photoFilter.applyEffect(bitmap, Grain())
                    }
                    "Highlight" -> {
                        photoFilter.applyEffect(bitmap, Highlight())
                    }
                    "Lomoish" -> {
                        photoFilter.applyEffect(bitmap, Lomoish())
                    }
                    "Flip Horizontally" -> {
                        photoFilter.applyEffect(bitmap, FlipHorizontally())
                    }
                    "Flip Vertically" -> {
                        photoFilter.applyEffect(bitmap, FlipVertically())
                    }
                    "Negative" -> {
                        photoFilter.applyEffect(bitmap, Negative())
                    }
                    "Posterize" -> {
                        photoFilter.applyEffect(bitmap, Posterize())
                    }
                    "Rotate" -> {
                        photoFilter.applyEffect(bitmap, Rotate())
                    }
                    "Saturate" -> {
                        photoFilter.applyEffect(bitmap, Saturate())
                    }
                    "Sepia" -> {
                        photoFilter.applyEffect(bitmap, Sepia())
                    }
                    "Sharpen" -> {
                        photoFilter.applyEffect(bitmap, Sharpen())
                    }
                    "Vignette" -> {
                        photoFilter.applyEffect(bitmap, Vignette())
                    }
                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                toast("${ImagePicker.getError(data)}")
            } else {
                toast("Task Cancelled")
            }
        }


}