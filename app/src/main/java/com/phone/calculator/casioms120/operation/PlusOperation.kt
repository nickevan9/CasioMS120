package com.phone.calculator.casioms120.operation

import com.phone.calculator.casioms120.operation.base.BinaryOperation
import com.phone.calculator.casioms120.operation.base.Operation

class PlusOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue),
    Operation {

    override fun getResult() = baseValue + secondValue
}
