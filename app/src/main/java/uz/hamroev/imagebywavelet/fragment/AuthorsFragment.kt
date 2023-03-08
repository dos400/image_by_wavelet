package uz.hamroev.imagebywavelet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.adapters.UserAdapter
import uz.hamroev.imagebywavelet.databinding.FragmentAuthorsBinding
import uz.hamroev.imagebywavelet.model.MyUser

class AuthorsFragment : Fragment() {


    lateinit var binding: FragmentAuthorsBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var list: ArrayList<MyUser>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorsBinding.inflate(layoutInflater, container, false)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        // will be authors

        loadUsers()
        userAdapter = UserAdapter(requireContext(), list)
        binding.rvUsers.adapter = userAdapter

        return binding.root
    }

    private fun loadUsers() {
        list = ArrayList()
        list.clear()
        list.add(MyUser("Doston Hamroyev","Android dasturchi ", R.drawable.fi_users))
        list.add(MyUser("Doston Hamroyev","Android dasturchi ", R.drawable.fi_users))
        list.add(MyUser("Doston Hamroyev","Android dasturchi ", R.drawable.fi_users))
    }


}