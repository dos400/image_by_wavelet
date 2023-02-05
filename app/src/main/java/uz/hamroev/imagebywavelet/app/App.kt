package uz.hamroev.imagebywavelet.app

import android.app.Application
import uz.hamroev.imagebywavelet.cache.Cache

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Cache.init(this)
    }
}