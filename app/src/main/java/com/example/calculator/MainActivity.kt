package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var result: EditText
    private lateinit var newNumber: EditText
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    private var op1: Double? = null
    private var op2: Double = 0.0
    private var pendingOp = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result = findViewById(R.id.result)
        newNumber = findViewById(R.id.newNumber)

        val btn0: Button = findViewById(R.id.btn0)
        val btn1: Button = findViewById(R.id.btn1)
        val btn2: Button = findViewById(R.id.btn2)
        val btn3: Button = findViewById(R.id.btn3)
        val btn4: Button = findViewById(R.id.btn4)
        val btn5: Button = findViewById(R.id.btn5)
        val btn6: Button = findViewById(R.id.btn6)
        val btn7: Button = findViewById(R.id.btn7)
        val btn8: Button = findViewById(R.id.btn8)
        val btn9: Button = findViewById(R.id.btn9)
        val btnDot: Button = findViewById(R.id.btnDot)

        val btnAdd: Button = findViewById(R.id.btnAdd)
        val btnMinus: Button = findViewById(R.id.btnMinus)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnDivide: Button = findViewById(R.id.btnDivide)
        val btnEquals: Button = findViewById(R.id.btnEquals)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        val btns = listOf(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnDot)
        for (btn in btns) btn.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            val value = newNumber.text.toString()

            if (value.isNotEmpty()) {
                performOperation(value, op)
            }
            pendingOp = op
            displayOperation.text = pendingOp
        }

        val opBtns = listOf(btnAdd, btnMinus, btnMultiply, btnDivide, btnEquals)
        for (btnOp in opBtns) btnOp.setOnClickListener(opListener)
    }

    private fun performOperation(value: String, operation: String) {
        if (op1 == null) {
            op1 = value.toDouble()
        } else {
            op2 = value.toDouble()
            if (pendingOp == "=") {
                pendingOp = operation
            }

            when (pendingOp) {
                "=" -> op1 = op2
                "/" -> op1 = if (op2 == 0.0) {
                    Double.NaN
                } else {
                    op1!! / op2
                }
                "*" -> op1 = op1!! * op2
                "-" -> op1 = op1!! - op2
                "+" -> op1 = op1!! + op2
            }
        }

        result.setText(op1.toString())
        newNumber.setText("")
    }
}
