package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator {
    @Override
    public int priority() {
        return 3;
    }

    @Override
    public Operand execute(Operand operandOne, Operand operandTwo) {
        int pow = 1;
        for(int i = 0; i < operandTwo.getValue(); i++){
            pow *= operandOne.getValue();
        }
        Operand result = new Operand(pow);
        return result;
    }
}
