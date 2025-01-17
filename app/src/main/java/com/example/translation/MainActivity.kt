package com.example.translation

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.translation.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var deviceLanguage : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = getSharedPreferences("lan", 0)
        val edit = pref.edit()

        val storedLanguage = pref.getString("language","")

        if(storedLanguage == ""){
            deviceLanguage = Locale.getDefault().language
        }else{
            storedLanguage?.let { Locale(it) }?.let { Locale.setDefault(it) }
        }

        Log.i("@@@@", "deviceLanguage: $deviceLanguage")

        binding.mcvTranslationKoreanButton.setOnClickListener {
            if (deviceLanguage == "ko") {
                setLocale("en")
                //Pref 에 저장

                edit.putString("language", "en")
                edit.apply()
            } else {
                setLocale("ko")
                edit.putString("language", "ko")
                edit.apply()

            }
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        createConfigurationContext(configuration)

        finish()
        startActivity(packageManager.getLaunchIntentForPackage(packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}