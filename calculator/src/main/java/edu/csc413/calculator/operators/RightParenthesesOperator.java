package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class RightParenthesesOperator extends Operator {
    @Override
    public int priority() {
        //giving this object the highest priority so the program focuses on the calculators within "( )"
        return 4;
    }

    @Override
    public Operand execute(Operand operandOne, Operand operandTwo) {
        return null;
    }
}
