package com.example.tugas2kalkulator_pamobile_raficahyaprastawa

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var inputText: TextView
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputText = findViewById(R.id.input_text)
        resultText = findViewById(R.id.result_text)

        val numberButtons = listOf(
            R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3,
            R.id.button_4, R.id.button_5, R.id.button_6,
            R.id.button_7, R.id.button_8, R.id.button_9
        )

        numberButtons.forEach { id ->
            findViewById<MaterialButton>(id).setOnClickListener {
                appendToInput((it as MaterialButton).text.toString())
            }
        }

        val operatorButtons = listOf(
            R.id.button_plus, R.id.button_minus, R.id.button_times,
            R.id.button_divide, R.id.button_dot,
            R.id.button_open_brucket, R.id.button_close_brucket
        )

        operatorButtons.forEach { id ->
            findViewById<MaterialButton>(id).setOnClickListener {
                appendToInput((it as MaterialButton).text.toString())
            }
        }

        findViewById<MaterialButton>(R.id.button_ac).setOnClickListener {
            inputText.text = ""
            resultText.text = ""
        }

        findViewById<MaterialButton>(R.id.button_c).setOnClickListener {
            val current = inputText.text.toString()
            if (current.isNotEmpty()) {
                inputText.text = current.substring(0, current.length - 1)
            }
        }

        findViewById<MaterialButton>(R.id.button_equals).setOnClickListener {
            calculateResult()
        }
    }

    private fun appendToInput(value: String) {
        inputText.append(value)
    }

    private fun calculateResult() {
        try {
            val expressionStr = inputText.text.toString().replace(":", "/")
            val expression = ExpressionBuilder(expressionStr).build()
            val result = expression.evaluate()

            // Ganti input dengan hasil
            inputText.text = result.toString()
            resultText.text = ""
        } catch (e: Exception) {
            resultText.text = "Error"
        }
    }
}
