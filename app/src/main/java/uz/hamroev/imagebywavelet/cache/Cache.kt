package uz.hamroev.imagebywavelet.cache

import android.content.Context
import android.content.SharedPreferences
import java.io.File

object Cache {

    private const val NAME = "imagebywavelet"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var language: String?
        get() = sharedPreferences.getString("language", "en")
        set(value) = sharedPreferences.edit() {
            if (value != null) {
                it.putString("language", value)
            }
        }

    var filterName: String?
        get() = sharedPreferences.getString("filtername", "None")
        set(value) = sharedPreferences.edit() {
            if (value != null) {
                it.putString("filtername", value)
            }
        }

    var imageFile: String?
        get() = sharedPreferences.getString("imagefile", "None")
        set(value) = sharedPreferences.edit() {
            if (value != null) {
                it.putString("imagefile", value)
            }
        }




   }

