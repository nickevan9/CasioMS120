package com.phone.calculator.casioms120.operation

import com.phone.calculator.casioms120.helpers.*
import com.phone.calculator.casioms120.operation.base.Operation
import com.phone.extension.calculatorms120.operation.PercentOperation

object OperationFactory {

    fun forId(id: String, baseValue: Double, secondValue: Double): Operation? {
        return when (id) {
            PLUS -> PlusOperation(baseValue, secondValue)
            MINUS -> MinusOperation(baseValue, secondValue)
            DIVIDE -> DivideOperation(baseValue, secondValue)
            MULTIPLY -> MultiplyOperation(baseValue, secondValue)
            PERCENT -> PercentOperation(baseValue, secondValue)

            else -> null
        }
    }
}
