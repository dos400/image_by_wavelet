package uz.hamroev.imagebywavelet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {


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


        return binding.root
    }


}