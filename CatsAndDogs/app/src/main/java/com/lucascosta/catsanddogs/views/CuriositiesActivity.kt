package com.lucascosta.catsanddogs.views

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lucascosta.catsanddogs.R
import com.lucascosta.catsanddogs.configs.MyPreferences
import com.lucascosta.catsanddogs.databinding.ActivityCuriositiesBinding
import com.lucascosta.catsanddogs.viewModel.CuriositiesViewModel

class CuriositiesActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCuriositiesBinding
    private lateinit var curiosityVM: CuriositiesViewModel

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

        binding.olaUsuario.text =
            getString(R.string.ola_usuario, MyPreferences(this).getString("nome"))

        curiosityVM = ViewModelProvider(this)[CuriositiesViewModel::class.java]
        curiosityVM.setCurrentAnimal("cat")

        setObserver()

        binding.catButton.setOnClickListener(this)
        binding.dogButton.setOnClickListener(this)
        binding.btnGerarFrase.setOnClickListener(this)
    }

    fun setObserver() {
        curiosityVM.getCurrentAnimal().observe(this, Observer {
            curiosityVM.newCuriosity(it)
        })

        curiosityVM.getCurrentCuriosity().observe(this, Observer { curiosity ->
            binding.curiosidade.text = curiosity
        })
    }

    override fun onClick(view: View) {
        if (view.id == R.id.catButton) {
            binding.catButton.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.amarelo))
            binding.dogButton.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.branco))

            curiosityVM.setCurrentAnimal("cat")

        } else if (view.id == R.id.dogButton) {
            binding.dogButton.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.amarelo))
            binding.catButton.imageTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.branco))

            curiosityVM.setCurrentAnimal("dog")

        } else if (view.id == R.id.btn_gerar_frase) {
            val currentAnimal = curiosityVM.getCurrentAnimal().value ?: "cat"
            curiosityVM.newCuriosity(currentAnimal)
        }
    }
}