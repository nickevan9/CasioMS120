package com.phone.calculator.casioms120.helpers

import android.content.Context
import android.util.Log
import com.phone.calculator.casioms120.R
import com.phone.calculator.casioms120.operation.OperationFactory

class CalculatorImpl(calculator: Calculator, private val context: Context) {
    private var displayedValueNumber: String? = null
    private var displayedFormulaNumber: String? = null

    var lastKey: String? = null
    private var mLastOperation: String? = null
    private var mCallback: Calculator? = calculator

    private var mAddOperation = false
    private var mAddSecondValue = false

    private var mIsFirstOperation = false
    private var mResetValue = false
    private var mWasPercentLast = false
    private var mFirstValue = 0.0
    private var mSecondValue = 0.0

    private var mMemoryValue = 0.0
    private var mMemoryState = false

    private var mCostValue = 0.0
    private var mSellValue = 0.0
    private var mMarValue = 0.0

    private var mSetState = false
    private var mSetValue = 0.0

    private var mResultTax = 0.0

    private var mStringCalculator = ""
    private var mCalculatorState = false

    init {
        resetValues()
        setValue("0")
        setFormula("")
    }

    private fun resetValueIfNeeded() {
        if (mResetValue) {
            displayedValueNumber = "0"
        }

        mResetValue = false
    }

    private fun resetValues() {
        mFirstValue = 0.0
        mSecondValue = 0.0
        mCostValue = 0.0
        mSellValue = 0.0
        mMarValue = 0.0
        mResetValue = false
        mLastOperation = ""
        displayedValueNumber = ""
        displayedFormulaNumber = ""
        mIsFirstOperation = true
        lastKey = ""
        mSetState = false
        mStringCalculator = ""
        mCalculatorState = false
        mAddOperation = false
        mAddSecondValue = false
    }

    fun handleReset() {
        resetValues()
        setValue("0")
        setFormula("")
        hiddenAllState()
    }

    private fun addDigit(number: Int) {
        if (mAddSecondValue) {
            displayedFormulaNumber += number
            val index = displayedFormulaNumber!!.toCharArray()
                .lastIndexOf(getSign(mLastOperation!!).single())
            val currentValue =
                displayedFormulaNumber!!.substring(index + 1, displayedFormulaNumber!!.length)
            mSecondValue = Formatter.stringToDouble(currentValue)
            setFormula(displayedFormulaNumber!!)
            Log.d("MSecondValue", currentValue)
        } else {

            val currentValue = displayedFormulaNumber
            val newValue = formatString(currentValue!! + number)
            mCallback!!.setVisibilityGT(false)

            if (mCalculatorState) {
                mStringCalculator = number.toString()
                setFormula(mStringCalculator)

            } else {
                mStringCalculator += number
                setFormula(mStringCalculator)
            }

            displayedFormulaNumber = newValue
        }
    }

    fun setValue(value: String) {
        mCallback!!.setValue(value, context)
        displayedValueNumber = value
    }

    private fun setFormula(value: String) {
        mCallback!!.setFormula(value, context)
        Log.d("mStringCalculator", mStringCalculator)
    }

    private fun decimalClicked() {
        var value = displayedValueNumber
        if (!value!!.contains(".")) {
            value += "."
            mStringCalculator += "."
            setFormula(mStringCalculator)
            displayedValueNumber = value
        }
    }

    private fun zeroClicked() {
        val value = displayedValueNumber
        if (value != "0") {
            addDigit(0)
        }
    }

    private fun getSign(lastOperation: String?) = when (lastOperation) {
        PLUS -> "+"
        MINUS -> "-"
        MULTIPLY -> "×"
        DIVIDE -> "÷"
        PERCENT -> "%"
        POWER -> "^"
        ROOT -> "√"
        FACTORIAL -> "!"
        else -> ""
    }

    fun numpadClicked(id: Int) {
        if (lastKey == EQUALS) {
            mLastOperation = EQUALS
        }

        lastKey = DIGIT
        resetValueIfNeeded()

        when (id) {
            R.id.btn_decimal -> decimalClicked()
            R.id.btn_0 -> {
                zeroClicked()
                mAddOperation = true
            }
            R.id.btn_1 -> {
                addDigit(1)
                mAddOperation = true
            }
            R.id.btn_2 -> {
                addDigit(2)
                mAddOperation = true
            }
            R.id.btn_3 -> {
                addDigit(3)
                mAddOperation = true
            }
            R.id.btn_4 -> {
                addDigit(4)
                mAddOperation = true
            }
            R.id.btn_5 -> {
                addDigit(5)
                mAddOperation = true
            }
            R.id.btn_6 -> {
                addDigit(6)
                mAddOperation = true
            }
            R.id.btn_7 -> {
                addDigit(7)
                mAddOperation = true
            }
            R.id.btn_8 -> {
                addDigit(8)
                mAddOperation = true
            }
            R.id.btn_9 -> {
                addDigit(9)
                mAddOperation = true
            }
        }
    }

    private fun getDisplayedNumberAsDouble() = Formatter.stringToDouble(displayedValueNumber!!)

    private fun formatString(str: String): String {
        // if the number contains a decimal, do not try removing the leading zero anymore, nor add group separator
        // it would prevent writing values like 1.02
        if (str.contains(".")) {
            return str
        }

        val doubleValue = Formatter.stringToDouble(str)
        return Formatter.doubleToString(doubleValue)
    }

    fun addZero() {
        if (lastKey == EQUALS) {
            mLastOperation = EQUALS
        }

        lastKey = DIGIT
        resetValueIfNeeded()
        addDigit(0)
        addDigit(0)
    }

//    fun resetMemory() {
//        mCallback!!.setVisibilityMemory(true, "M")
//        mMemoryState = true
//        mMemoryValue = 0.0
//    }
//
//    fun setMemory(value: String) {
//        if (mMemoryState) {
//            mCallback!!.setVisibilityMemory(true, value)
//            if (value == "M-") {
//                subToMemory()
//            } else {
//                plusToMemory()
//            }
//        }
//    }
//
//    private fun plusToMemory() {
//        mMemoryValue += if (mBaseValue != 0.0) {
//            mBaseValue
//        } else {
//            displayedValueNumber?.toDoubleOrNull()!!
//        }
//        mResetValue = true
//        mCalculatorState = true
//    }
//
//    private fun subToMemory() {
//        mMemoryValue -= if (mBaseValue != 0.0) {
//            mBaseValue
//        } else {
//            displayedValueNumber?.toDoubleOrNull()!!
//        }
//        mResetValue = true
//        mCalculatorState = true
//    }
//
//    fun memoryResult() {
//        mBaseValue = mMemoryValue
//        updateResult(mBaseValue)
//    }
//
//
//    fun setSet() {
//        mCallback!!.setVisibilitySET(true)
//        mSetState = true
//    }
//
//    fun setCost() {
//        mCostValue = displayedValueNumber?.toDoubleOrNull()!!
//        mCallback!!.setVisibilityMargin(true, "COST")
//        mResetValue = true
//        mCalculatorState = true
//    }
//
//    fun setSell() {
//        mSellValue = displayedValueNumber?.toDoubleOrNull()!!
//        mCallback!!.setVisibilityMargin(true, "SELL")
//
//        calculatorMargin()
//    }
//
//    fun taxAdd() {
//        if (mSetValue != 0.0) {
//            mCallback!!.setVisibilityTAX(true, "TAX+")
//            mResultTax = displayedValueNumber?.toDoubleOrNull()!! * (100 + mSetValue) / 100
//            updateResult(mResultTax)
//        }
//    }
//
//    fun taxSub() {
//        if (mSetValue != 0.0) {
//            mCallback!!.setVisibilityTAX(true, "TAX−")
//            mResultTax = displayedValueNumber?.toDoubleOrNull()!! * (100 - mSetValue) / 100
//            updateResult(mResultTax)
//        }
//    }
//
//    private fun calculatorMargin() {
//        mMarValue = mSellValue - mCostValue
//        mCallback!!.setVisibilityMargin(true, "MAR")
//        updateResult(mMarValue)
//    }

    private fun hiddenAllState() {
        mCallback!!.setVisibilityMargin(false, "")
        mCallback!!.setVisibilityGT(false)
        mCallback!!.setVisibilityMemory(false, "")
        mCallback!!.setVisibilityTAX(false, "")
        mCallback!!.setVisibilitySET(false)


    }

    fun addOperation(operation: String) {
        if (mAddOperation) {
            if (mSecondValue != 0.0) {
                calculateResult()
                displayedFormulaNumber += getSign(operation)
                lastKey = operation
                mLastOperation = operation
                setFormula(displayedFormulaNumber!!)
                mAddOperation = false
                mAddSecondValue = true
            } else {
                mFirstValue = Formatter.stringToDouble(displayedFormulaNumber!!)
                displayedFormulaNumber += getSign(operation)

                lastKey = operation
                mLastOperation = operation

                setFormula(displayedFormulaNumber!!)
                mAddOperation = false
                mAddSecondValue = true
            }

        }
    }

    private fun calculateResult() {
        val operation = OperationFactory.forId(mLastOperation!!, mFirstValue, mSecondValue)

        if (operation != null) {
            val result = Formatter.doubleToString(operation.getResult())
            mFirstValue = operation.getResult()
            setValue(result)
        }
    }
}
