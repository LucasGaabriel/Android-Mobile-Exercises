package com.lucascosta.impostoderenda

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lucascosta.impostoderenda.databinding.ActivityMainBinding
import java.security.InvalidParameterException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding;

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


        binding.buttonCalcular.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_calcular -> {
                var salario: Double = 0.0
                var gastos: Double = 0.0
                try {
                    salario = binding.salario.text.toString().toDouble()
                    gastos = binding.gastos.text.toString().toDouble()
                } catch (_: NumberFormatException) {
                    val toastText = "(!) Campos inválidos!"
                    val duration = Toast.LENGTH_SHORT
                    Toast.makeText(this, toastText, duration).show()

                    return
                }

                val imposto = calculaIR(salario, gastos)
                binding.resultado.text = "R$ %.2f".format(imposto)

                val toastText = "Imposto de renda calculado com sucesso!"
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(this, toastText, duration).show()
            }
        }
    }

    fun calculaIR(salario: Double, gastos: Double): Double {
        val isencao = 1903.98

        val aliquota = when (salario) {
            in 0.0..1903.98 -> 0.0
            in 1903.99..2826.65 -> 0.075
            in 2826.66..3751.05 -> 0.15
            in 3751.06..4664.68 -> 0.225
            in 4664.69..Double.MAX_VALUE -> 0.275
            else -> throw InvalidParameterException("Salário não especificado corretamente")
        }

        val imposto = ((salario - isencao) * aliquota) - gastos

        if (imposto < 0)
            return 0.0

        return imposto
    }
}