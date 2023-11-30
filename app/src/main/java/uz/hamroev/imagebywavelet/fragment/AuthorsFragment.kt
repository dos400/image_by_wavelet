package uz.hamroev.imagebywavelet.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.hamroev.imagebywavelet.R
import uz.hamroev.imagebywavelet.adapters.UserAdapter
import uz.hamroev.imagebywavelet.cache.Cache
import uz.hamroev.imagebywavelet.databinding.FragmentAuthorsBinding
import uz.hamroev.imagebywavelet.model.MyUser

class AuthorsFragment : Fragment() {


    lateinit var binding: FragmentAuthorsBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var list: ArrayList<MyUser>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        when (Cache.language) {

            "uz" -> {
                list.add(
                    MyUser(
                        "Beknazarova\n" +
                                "Saida\n" +
                                "Safibullayevna",
                        "Professor\n" +
                                "t.f.d.\n" +
                                "Muhammad al-Xorazmiy nomidagi Toshkent Аxborot texnologiyalari universiteti Аudiovizual texnologiyalar kafedrasi muduri",
                        R.drawable.ic_saida
                    )
                )
                list.add(
                    MyUser(
                        "Abdumajidov \nDostonbek \nBotir o'g'li",
                        "TATU magistr talabasi \nVideotexnalogiyalar yo'nalishi",
                        R.drawable.ic_doston_abdumajidov
                    )
                )
            }

            "ru" -> {
                list.add(
                    MyUser(
                        "Бекназарова \nСаида \nСафибуллаевна",
                        "Доктор технических наук, Профессор\n" +
                                "Заведующий кафедрой Аудиовизуальных технологий Ташкентского университета информационных технологий имени Мухаммада ал-Хоразмий",
                        R.drawable.ic_saida
                    )
                )
                list.add(
                    MyUser(
                        "Abdumajidov \nDostonbek \nBotir o'g'li",
                        "TATU magistr talabasi \nVideotexnalogiyalar yo'nalishi",
                        R.drawable.ic_doston_abdumajidov
                    )
                )

            }

            "en" -> {
                list.add(
                    MyUser(
                        "Beknazarova\n" +
                                "Saida\n" +
                                "Safibullaevna",
                        "Professor, DSc of technical science/ Head of department TUIT",
                        R.drawable.ic_saida
                    )
                )
                list.add(
                    MyUser(
                        "Abdumajidov \nDostonbek \nBotir o'g'li",
                        "TATU magistr talabasi \nVideotexnalogiyalar yo'nalishi",
                        R.drawable.ic_doston_abdumajidov
                    )
                )

            }
        }

    }


}