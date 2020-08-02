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
    }

    fun onDecimalPoint(view: View) {
        if(lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var left = splitValue[0]
                    var right = splitValue[1]

                    if(!prefix.isEmpty()) {
                        left = prefix + left
                    }

                    tvInput.text =
                        removeZeroAfterDot((left.toDouble() - right.toDouble()).toString())

                } else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var left = splitValue[0]
                    var right = splitValue[1]

                    if(!prefix.isEmpty()) {
                        left = prefix + left
                    }

                    tvInput.text =
                        removeZeroAfterDot((left.toDouble() + right.toDouble()).toString())

                } else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var left = splitValue[0]
                    var right = splitValue[1]

                    if(!prefix.isEmpty()) {
                        left = prefix + left
                    }

                    tvInput.text =
                        removeZeroAfterDot((left.toDouble() / right.toDouble()).toString())

                } else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var left = splitValue[0]
                    var right = splitValue[1]

                    if(!prefix.isEmpty()) {
                        left = prefix + left
                    }

                    tvInput.text =
                        removeZeroAfterDot((left.toDouble() * right.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if(result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) : Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            (value.contains("+") || value.contains("-") ||
                    value.contains("*") || value.contains("/"))
        }
    }

}