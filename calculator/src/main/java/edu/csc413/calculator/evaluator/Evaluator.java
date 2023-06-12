package edu.csc413.calculator.evaluator;

import edu.csc413.calculator.operators.*;

import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {

  private Stack<Operand> operandStack;
  private Stack<Operator> operatorStack;
  private StringTokenizer expressionTokenizer;
  private final String delimiters = " +/*-^()"; // added parentheses to the delimiter

  public Evaluator() {
    operandStack = new Stack<>();
    operatorStack = new Stack<>();
  }
  public void processor(){
    Operator operator = operatorStack.pop();
    Operand operand2 = operandStack.pop();
    Operand operand1 = operandStack.pop();
    operandStack.push(operator.execute(operand1, operand2));
  }

  public int evaluateExpression(String expression ) throws InvalidTokenException {
    String expressionToken;

    // The 3rd argument is true to indicate that the delimiters should be used
    // as tokens, too. But, we'll need to remember to filter out spaces.
    this.expressionTokenizer = new StringTokenizer(expression, this.delimiters, true);

    // initialize operator stack - necessary with operator priority schema
    // the priority of any operator in the operator stack other than
    // the usual mathematical operators - "+-*/" - should be less than the priority
    // of the usual operators


    while (this.expressionTokenizer.hasMoreTokens()) {
      // filter out spaces
      if (!(expressionToken = this.expressionTokenizer.nextToken()).equals(" ")) {
        // check if token is an operand
        if (Operand.check(expressionToken)) {
          operandStack.push(new Operand(expressionToken));
        } else {
          if (!Operator.check(expressionToken)) {
            throw new InvalidTokenException(expressionToken);
          }

          // TODO Operator is abstract - these two lines will need to be fixed:
          // The Operator class should contain an instance of a HashMap,
          // and values will be instances of the Operators.  See Operator class
          // skeleton for an example.
          Operator newOperator = Operator.getOperator(expressionToken);

          //updated old while loop conditions so that we check if the operator stack isnt empty and also check if the token isnt a "("
          while (!operatorStack.isEmpty() && operatorStack.peek().priority() >= newOperator.priority() && !expressionToken.equals("(")) {

            // note that when we eval the expression 1 - 2 we will
            // push the 1 then the 2 and then do the subtraction operation
            // This means that the first number to be popped is the
            // second operand, not the first operand - see the following code
            processor();
          }
          operatorStack.push(newOperator);
          //if statement that checks for ")"
          if(expressionToken.equals(")")){
            //if found, we pop it out the stack
            operatorStack.pop();
            //then loop out processor function up until we find a "(" operator since we only want to process the current ( )
            while (!(operatorStack.peek().priority() == 0) ) {
              processor();
            }
            //then we pop the next operator("(")
            operatorStack.pop();
          }
        }
      }
    }

    // Control gets here when we've picked up all of the tokens; you must add
    // code to complete the evaluation - consider how the code given here
    // will evaluate the expression 1+2*3
    // When we have no more tokens to scan, the operand stack will contain 1 2
    // and the operator stack will have + * with 2 and * on the top;
    // In order to complete the evaluation we must empty the stacks,
    // that is, we should keep evaluating the operator stack until it is empty;
    // Suggestion: create a method that processes the operator stack until empty.

    //finally, we process the remaining evaluations until the stack is empty
    while (!operatorStack.isEmpty()) {
      processor();
    }
    //returning the final value
    return operandStack.pop().getValue();
  }
}
