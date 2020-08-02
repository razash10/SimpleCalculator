package com.razash.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
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
        tvInput.append((view as Button).text)
        lastNumeric = true

    }

    fun onClear(view: View) {
        tvInput.text = ""
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
        rightNum = tvInput.text.toString()
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
        if(result.contains(".")) {
            while(value.endsWith("0")) {
                value = value.substring(0, value.length - 1)
            }
        }
        if(value.endsWith(".")) {
            value = value.substring(0, value.length - 1)
        }
        return value
    }

    fun onOperator(view: View) {
        view as Button
        if(view.text == "-" && tvInput.text.isEmpty()) {
                tvInput.append("-")
        }
        else if(!tvInput.text.isEmpty() && lastOperator.isEmpty()){
            leftNum = tvInput.text.toString()
            lastOperator = view.text.toString()
            tvInput.text = ""
            lastNumeric = false
            lastDot = false
        }
    }

}