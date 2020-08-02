package com.razash.simplecalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var lastNumeric = false
    private var lastDot = false
    private var lastOperator = ""
    private var leftNum = ""
    private var rightNum = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setLayoutDirection(window.decorView, ViewCompat.LAYOUT_DIRECTION_LTR)
    }

    fun onDigit(view: View) {
        if((view as Button).text.toString() == "0" && tvInput.text.toString() == "0") {
            return
        }
        tvInput.append(view.text)
        lastNumeric = true

    }

    fun onClear(view: View) {
        tvInput.text = ""
        tvEquation.text = ""
        lastNumeric = false
        lastDot = false
        lastOperator = ""
        leftNum = ""
        rightNum = ""
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if(lastOperator.isEmpty() || tvInput.text.isEmpty()) {
            return
        }

        rightNum = tvInput.text.toString()

        val equation = if(leftNum.startsWith("-") && rightNum.startsWith("-")) {
            "($leftNum) $lastOperator ($rightNum) ="
        } else if(!leftNum.startsWith("-") && rightNum.startsWith("-")) {
            "$leftNum $lastOperator ($rightNum) ="
        } else if(leftNum.startsWith("-") && !rightNum.startsWith("-")) {
            "($leftNum) $lastOperator $rightNum ="
        } else {
            "$leftNum $lastOperator $rightNum ="
        }

        tvEquation.text = equation

        var output = ""

        try {
            when (lastOperator) {
                "+" -> output = (leftNum.toDouble() + rightNum.toDouble()).toString()
                "-" -> output = (leftNum.toDouble() - rightNum.toDouble()).toString()
                "*" -> output = (leftNum.toDouble() * rightNum.toDouble()).toString()
                "/" -> output = (leftNum.toDouble() / rightNum.toDouble()).toString()
            }
        } catch(e: ArithmeticException) { e.printStackTrace() }

        tvInput.text = finalizeResult(output)

        lastOperator = ""
        rightNum = ""
        leftNum = tvInput.text.toString()
    }

    private fun finalizeResult(result: String) : String {
        var value = result
        var valueNum = value.toDouble()
        valueNum = "%.12f".format(valueNum).toDouble()
        value = valueNum.toString()
        if(result.contains(".")) {
            while(value.endsWith("0")) {
                value = value.substring(0, value.length - 1)
            }
        }
        if(value.endsWith(".")) {
            value = value.substring(0, value.length - 1)
        }
        if(value == "-0") {
            value = "0"
        }
        return value
    }

    fun onOperator(view: View) {
        view as Button
        if(view.text == "-" && tvInput.text.isEmpty()) {
                tvInput.append("-")
        }
        else if(tvInput.text.isNotEmpty() && lastOperator.isEmpty() &&
                !tvInput.text.endsWith("-")){
            leftNum = tvInput.text.toString()
            lastOperator = view.text.toString()
            val result = tvInput.text.toString()
            val equation = "$result $lastOperator"
            tvEquation.text = equation
            tvInput.text = ""
            lastNumeric = false
            lastDot = false
        }
    }

    fun onDelete(view: View) {
        if(tvInput.text.isNotEmpty()) {
            tvInput.text = tvInput.text.substring(0, tvInput.length() - 1)
        }
    }

}