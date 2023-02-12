package uz.hamroev.imagebywavelet.fragment


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mukesh.imageproccessing.filters.*
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.adapters.ImageFilterAdapter
import uz.hamroev.imagebywavelet.adapters.NavAdapter
import uz.hamroev.imagebywavelet.cache.Cache
import uz.hamroev.imagebywavelet.databinding.DialogLanguageBinding
import uz.hamroev.imagebywavelet.databinding.FragmentHomeBinding
import uz.hamroev.imagebywavelet.model.ImageFilter
import uz.hamroev.imagebywavelet.model.Nav
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding

    //nav menu list and navAdapter to down
    private lateinit var listNav: ArrayList<Nav>
    private lateinit var navAdapter: NavAdapter

    // image filter list and adapter in down
    private lateinit var listImageFilter: ArrayList<ImageFilter>
    private lateinit var imageFilterAdapter: ImageFilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        binding.menuButton.setOnClickListener {
            binding.drawerLayout.open()
        }

        loadImageFilter()
        imageFilterAdapter = ImageFilterAdapter(
            requireContext(),
            listImageFilter,
            object : ImageFilterAdapter.OnClickListerImageFilterItem {
                override fun onClick(imageFilter: ImageFilter, position: Int) {
                    val bundle: Bundle = Bundle()
                    bundle.putSerializable("filter", imageFilter)
                    findNavController().navigate(R.id.resultFragment, bundle)
                }
            })
        binding.rvTheme.adapter = imageFilterAdapter



        loadNav()
        navAdapter = NavAdapter(requireContext(), listNav, object : NavAdapter.OnNavClickListener {
            override fun onCLick(nav: Nav, position: Int) {
                when (position) {
                    0 -> {
                        binding.drawerLayout.close()
                    }
                    1 -> {
                        binding.drawerLayout.close()
                        findNavController().navigate(R.id.savedFragment)
                    }
                    2 -> {
                        binding.drawerLayout.close()
                        findNavController().navigate(R.id.downloadedFragment)
                    }
                    3 -> {
                        binding.drawerLayout.close()
                        findNavController().navigate(R.id.aboutAppFragment)
                    }
                    4 -> {
                        binding.drawerLayout.closeDrawers()
                        val alertDialog = android.app.AlertDialog.Builder(binding.root.context)
                        val dialog = alertDialog.create()
                        val bindingLanguage =
                            DialogLanguageBinding.inflate(LayoutInflater.from(requireContext()))
                        dialog.setView(bindingLanguage.root)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setCancelable(true)


                        bindingLanguage.russian.setOnClickListener {
                            Cache.language = "ru"
                            setAppLocale(requireContext(), "ru")
                            findNavController().popBackStack()
                            findNavController().navigate(R.id.homeFragment)
                            dialog.dismiss()
                        }
                        bindingLanguage.uk.setOnClickListener {
                            Cache.language = "en"
                            setAppLocale(requireContext(), "en")
                            findNavController().popBackStack()
                            findNavController().navigate(R.id.homeFragment)
                            dialog.dismiss()
                        }
                        bindingLanguage.uzb.setOnClickListener {
                            Cache.language = "uz"
                            setAppLocale(requireContext(), "uz")
                            findNavController().popBackStack()
                            findNavController().navigate(R.id.homeFragment)
                            dialog.dismiss()
                        }


                        dialog.show()

                    }
                    5 -> {
                        binding.drawerLayout.close()
                        findNavController().navigate(R.id.authorsFragment)
                    }
                    6 -> {
                        try {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "text/plain"
                            intent.putExtra(Intent.EXTRA_SUBJECT, "IMAGE BY WAVELET")
                            val shareMessage =
                                "https://play.google.com/store/apps/details?id=${activity!!.packageName}"
                            intent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                            startActivity(Intent.createChooser(intent, "share by Doston"))
                        } catch (e: Exception) {
                        }
                        binding.drawerLayout.close()
                    }
                    7 -> {
                        try {
                            val uri: Uri =
                                Uri.parse("market://details?id=${activity!!.packageName}")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            val uri: Uri =
                                Uri.parse("http://play.google.com/store/apps/details?id=${activity!!.packageName}")
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        binding.drawerLayout.close()
                    }
                    8 -> {
                        activity?.finish()
                    }
                }
            }
        })
        binding.rvNav.adapter = navAdapter





        return binding.root
    }

    private fun loadImageFilter() {
        listImageFilter = ArrayList()
        listImageFilter.clear()
        listImageFilter.add(ImageFilter("None", None(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("AutoFix", AutoFix(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Brightness", Brightness(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Contrast", Contrast(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Temperature",Temperature(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Grayscale", Grayscale(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("CrossProcess", CrossProcess(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Documentary", Documentary(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("DuoTone", DuoTone(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Tint", Tint(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Fill-Light", FillLight(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("FishEye", FishEye(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Grain", Grain(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Highlight", Highlight(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Lomoish", Lomoish(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Flip Horizontally", FlipHorizontally(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Flip Vertically", FlipVertically(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Negative", Negative(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Posterize", Posterize(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Rotate", Rotate(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Saturate", Saturate(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Sepia", Sepia(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Sharpen", Sharpen(), R.drawable.eiffel))
        listImageFilter.add(ImageFilter("Vignette",Vignette(), R.drawable.eiffel))
    }

    private fun loadNav() {
        listNav = ArrayList()
        listNav.clear()
        listNav.add(Nav(requireActivity().resources.getString(R.string.main), R.drawable.fi_home))
        listNav.add(
            Nav(
                requireActivity().resources.getString(R.string.save),
                R.drawable.fi_bookmark
            )
        )
        listNav.add(
            Nav(
                requireActivity().resources.getString(R.string.downloaded),
                R.drawable.fi_download
            )
        )
        listNav.add(Nav(requireActivity().resources.getString(R.string.info), R.drawable.fi_info))
        listNav.add(
            Nav(
                requireActivity().resources.getString(R.string.language),
                R.drawable.fi_globe
            )
        )
        listNav.add(
            Nav(
                requireActivity().resources.getString(R.string.authors),
                R.drawable.fi_users
            )
        )
        listNav.add(
            Nav(
                requireActivity().resources.getString(R.string.share),
                R.drawable.fi_share_2
            )
        )
        listNav.add(Nav(requireActivity().resources.getString(R.string.rate), R.drawable.fi_star))
        listNav.add(
            Nav(
                requireActivity().resources.getString(R.string.exit),
                R.drawable.fi_log_out
            )
        )
    }

    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )
    }

}