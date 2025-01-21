package com.lucascosta.catsanddogs.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lucascosta.catsanddogs.R
import com.lucascosta.catsanddogs.databinding.ActivityMainBinding
import com.lucascosta.catsanddogs.configs.MyPreferences

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (MyPreferences(this).getString("nome") != ""){
            startActivity(Intent(this, CuriositiesActivity::class.java))
            finish()
        }

        binding.btnGuardar.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_guardar){
            val sp = MyPreferences(this)
            sp.setString("nome", binding.nome.text.toString())
            startActivity(Intent(this, CuriositiesActivity::class.java))
            finish()
        }
    }
}