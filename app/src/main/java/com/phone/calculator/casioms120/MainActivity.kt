package com.phone.calculator.casioms120

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phone.calculator.casioms120.helpers.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Calculator {

    lateinit var calc: CalculatorImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calc = CalculatorImpl(this, applicationContext)


        btn_add_0.setOnClickListener { calc.addZero() }
        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id)}
        }

        btn_plus.setOnClickListener { calc.addOperation(PLUS) }
        btn_minus.setOnClickListener { calc.addOperation(MINUS) }
        btn_multiply.setOnClickListener { calc.addOperation(MULTIPLY) }
        btn_divide.setOnClickListener { calc.addOperation(DIVIDE) }
        btn_percent.setOnClickListener { calc.addOperation(PERCENT) }


    }

    private fun getButtonIds() =
        arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)


    override fun setVisibilityGT(visibility: Boolean) {

    }

    override fun setVisibilityMemory(visibility: Boolean, value: String) {

    }

    override fun setVisibilityTAX(visibility: Boolean, value: String) {

    }

    override fun setVisibilityMargin(visibility: Boolean, value: String) {

    }

    override fun setVisibilitySET(visibility: Boolean) {

    }

    override fun setValue(value: String, context: Context) {
        tv_result.text = value
    }

    override fun setFormula(value: String, context: Context) {
        tv_formular.text = value
    }
}
