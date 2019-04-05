package skamila.calculator.api;

import java.math.BigDecimal;
import java.util.Stack;

import skamila.calculator.api.operation.Operation;

public class ReversePolishNotation {

    private Stack<BigDecimal> numbersStack;

    public ReversePolishNotation() {

        numbersStack = new Stack<>();

    }

    public void putNumber(BigDecimal number) {

        numbersStack.add(number);

    }

    public BigDecimal doOperation(Operation operation) {

        BigDecimal a = numbersStack.pop();
        BigDecimal b = numbersStack.pop();
        BigDecimal result = operation.doOperation(b, a);
        numbersStack.push(result);

        return result;

    }

}
