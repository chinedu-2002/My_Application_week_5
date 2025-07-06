package com.example.my_application_week_5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var number1: EditText
    private lateinit var number2: EditText
    private lateinit var result: TextView

    private lateinit var buttonAdd: Button
    private lateinit var buttonMinus: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonDiv: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // I want edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom)
            insets
        }

        // Bind all my views
        number1        = findViewById(R.id.number1)
        number2        = findViewById(R.id.number2)
        result         = findViewById(R.id.result)

        buttonAdd      = findViewById(R.id.button_add)
        buttonMinus    = findViewById(R.id.button_minus)
        buttonMultiply = findViewById(R.id.button_multiply)
        buttonDiv      = findViewById(R.id.button_div)

        // I want to hook up the buttons
        buttonAdd.setOnClickListener      { calculate { a, b -> a + b } }
        buttonMinus.setOnClickListener    { calculate { a, b -> a - b } }
        buttonMultiply.setOnClickListener { calculate { a, b -> a * b } }

        // Divide with upfront zero/divisor checks
        buttonDiv.setOnClickListener {
            val txt2 = number2.text.toString().trim()
            val b = txt2.toIntOrNull()
            when {
                txt2.isEmpty()       -> result.text = "Total = 0"            // or a warning
                b == null            -> result.text = "Total = 0"            // invalid format
                b == 0               -> result.text = "Total = 0"            // can’t divide by zero
                else                 -> calculate { a, bb -> a / bb }
            }
        }
    }

    /**
     * Parses inputs, applies the operation, and shows "Total = X" in your TextView.
     * Always returns an Int so the lambda stays type-safe.
     */
    private inline fun calculate(operation: (Int, Int) -> Int) {
        val txt1 = number1.text.toString().trim()
        val txt2 = number2.text.toString().trim()

        if (txt1.isEmpty() || txt2.isEmpty()) {
            result.text = "Total = 0"
            return
        }

        val a = txt1.toIntOrNull()
        val b = txt2.toIntOrNull()
        if (a == null || b == null) {
            result.text = "Total = 0"
            return
        }

        // my magic line:
        val output = operation(a, b)
        result.text = "Total = $output"
    }
}
