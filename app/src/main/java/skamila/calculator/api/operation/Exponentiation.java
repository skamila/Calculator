package skamila.calculator.api.operation;

import java.math.BigDecimal;

import skamila.calculator.api.exceptions.NegativeNumberException;

public class Exponentiation implements Operation {

    @Override
    public BigDecimal doOperation(BigDecimal a, BigDecimal b) throws NegativeNumberException {

        if (a.doubleValue() >= 0) {
            return a.pow(b.intValue());
        } else {
            throw new NegativeNumberException();
        }

    }

    @Override
    public String getSymbol() {
        return "^";
    }

}
