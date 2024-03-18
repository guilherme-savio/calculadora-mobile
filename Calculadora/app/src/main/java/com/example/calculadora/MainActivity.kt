package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.app.AlertDialog
import android.content.Context

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val conta = findViewById<TextView>(R.id.conta);
        val resultado = findViewById<TextView>(R.id.resultado);


        findViewById<Button>(R.id.zero).setOnClickListener { AddConta("0", false, conta) }
        findViewById<Button>(R.id.um).setOnClickListener { AddConta("1", false, conta) }
        findViewById<Button>(R.id.dois).setOnClickListener { AddConta("2", false, conta) }
        findViewById<Button>(R.id.tres).setOnClickListener { AddConta("3", false, conta) }
        findViewById<Button>(R.id.quatro).setOnClickListener { AddConta("4", false, conta) }
        findViewById<Button>(R.id.cinco).setOnClickListener { AddConta("5", false, conta) }
        findViewById<Button>(R.id.seis).setOnClickListener { AddConta("6", false, conta) }
        findViewById<Button>(R.id.sete).setOnClickListener { AddConta("7", false, conta) }
        findViewById<Button>(R.id.oito).setOnClickListener { AddConta("8", false, conta) }
        findViewById<Button>(R.id.nove).setOnClickListener { AddConta("9", false, conta) }

        // Operadores
        findViewById<Button>(R.id.soma).setOnClickListener { AddConta("+", true, conta) }
        findViewById<Button>(R.id.sub).setOnClickListener { AddConta("-", true, conta) }
        findViewById<Button>(R.id.div).setOnClickListener { AddConta("/", true, conta) }
        findViewById<Button>(R.id.mult).setOnClickListener { AddConta("*", true, conta) }
        findViewById<Button>(R.id.igual).setOnClickListener { GetResultado(conta, resultado, this) }


        // Botão para limpar
        findViewById<Button>(R.id.clear).setOnClickListener {
           conta.text = ""
            resultado.text = ""
        }

    }

    fun AddConta(char: String, operador: Boolean, conta: TextView){
        if(conta.text.isEmpty() && operador){
            exibirAlertDialog("Erro de formato", "Necessário adicionar algum valor antes do operador.", this)
        } else {
            conta.append(char);
        }

    }

    fun GetResultado(conta: TextView, resultado: TextView, context: Context) {
        val expressao = conta.text.toString()

        val operadores = arrayOf("+", "-", "*", "/")

        val operador = operadores.firstOrNull { expressao.contains(it) }
            ?: run {
                exibirAlertDialog("Operador inválido", "Nenhum operador válido encontrado na expressão: $expressao", context)
                return
            }

        val numeros = expressao.split(operador)

        val numero1 = numeros[0].toIntOrNull()
        val numero2 = numeros[1].toIntOrNull()

        if (numero1 == null || numero2 == null) {
            exibirAlertDialog("Erro de formato", "Formato de expressão inválido: $expressao", context)
            return
        }

        val resultadoCalculo = when (operador) {
            "+" -> numero1 + numero2
            "-" -> numero1 - numero2
            "*" -> numero1 * numero2
            "/" -> {
                if (numero2 == 0) {
                    exibirAlertDialog("Erro de divisão", "Divisão por zero não é permitida.", context)
                    return
                } else {
                    numero1 / numero2
                }
            }
            else -> {
                exibirAlertDialog("Operador inválido", "Operador desconhecido: $operador", context)
                return
            }
        }

        resultado.text = resultadoCalculo.toString()
    }

    fun exibirAlertDialog(titulo: String, mensagem: String, context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.apply {
            setTitle(titulo)
            setMessage(mensagem)
            setPositiveButton("OK", null)
            create().show()
        }
    }

}