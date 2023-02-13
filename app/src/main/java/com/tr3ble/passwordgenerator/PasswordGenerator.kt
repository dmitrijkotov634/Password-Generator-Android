package com.tr3ble.passwordgenerator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.color.DynamicColors
import com.google.android.material.slider.Slider
import com.tr3ble.passwordgenerator.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var currentPassword: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivityIfAvailable(this)

        super.onCreate(savedInstanceState)

        binding.passwordLengthSeekBar.addOnSliderTouchListener(object :
            Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {
                binding.passwordLengthTextView.text = slider.value.toString()
                binding.generateButton.callOnClick()
            }
        })

        binding.generateButton.setOnClickListener {
            val random = Random
            val passwordLength = binding.passwordLengthSeekBar.value.toInt()
            val password = CharArray(passwordLength) {
                val ranges = listOf('a'..'z', 'A'..'Z', '0'..'9')
                val range = ranges.random(random)
                range.random(random)
            }

            currentPassword = password.joinToString("")
            binding.passwordTextView.text = "Password: $currentPassword"
        }

        binding.copyButton.setOnClickListener {
            val clipboard =
                ContextCompat.getSystemService(applicationContext, ClipboardManager::class.java)
            val clip = ClipData.newPlainText(
                "Password",
                currentPassword
            )
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.TIRAMISU)
                Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show()
            clipboard?.setPrimaryClip(clip)
        }

        setContentView(binding.root)

        binding.generateButton.callOnClick()
    }
}
