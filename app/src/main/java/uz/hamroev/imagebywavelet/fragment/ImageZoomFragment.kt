package uz.hamroev.imagebywavelet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.databinding.FragmentImageZoomBinding

class ImageZoomFragment : Fragment() {


    lateinit var binding: FragmentImageZoomBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageZoomBinding.inflate(layoutInflater, container, false)


        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        // this window can zoom any image

        

        return binding.root
    }


}