package uz.hamroev.imagebywavelet

import android.app.Activity
import android.content.Intent
import android.graphics.text.LineBreaker
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.hamroev.imagebywavelet.activity.HomeActivity
import uz.hamroev.imagebywavelet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        hideSystemBars()
        supportActionBar?.hide()
        startAnimation()

        lifecycleScope.launch {
            delay(2700)
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }






    }


    private fun Activity.hideSystemBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val decorView = window.decorView
            val windowInsetsController = decorView.windowInsetsController ?: return
            windowInsetsController.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            windowInsetsController.hide(WindowInsets.Type.systemBars())
        } else {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startAnimation() {
        val animTeam = AnimationUtils.loadAnimation(this, R.anim.anim_intro_team)
        val animVersion = AnimationUtils.loadAnimation(this, R.anim.anim_intro_version)
        val animImage = AnimationUtils.loadAnimation(this, R.anim.anim_intro_image)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.infoApp.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }

        binding.titleAppName.animateText("History Uz")
        binding.titleAppName.setCharacterDeley(100)


        binding.teamTv.startAnimation(animTeam)
        binding.versionTv.startAnimation(animVersion)
        binding.image.startAnimation(animImage)
        binding.infoApp.startAnimation(animImage)


    }




}