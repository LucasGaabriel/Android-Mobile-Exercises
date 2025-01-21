package com.lucascosta.catsanddogs.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lucascosta.catsanddogs.R
import com.lucascosta.catsanddogs.configs.MyPreferences
import com.lucascosta.catsanddogs.databinding.ActivityCuriositiesBinding

class CuriositiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuriositiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuriositiesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.olaUsuario.text = getString(R.string.ola_usuario, MyPreferences(this).getString("nome"))
    }
}