package uz.hamroev.imagebywavelet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.databinding.FragmentDownloadedBinding


class DownloadedFragment : Fragment() {


    lateinit var binding: FragmentDownloadedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDownloadedBinding.inflate(layoutInflater, container, false)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        // here will be downloaded images

        return binding.root
    }


}