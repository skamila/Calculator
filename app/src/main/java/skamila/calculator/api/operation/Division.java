package skamila.calculator.api.operation;

import java.math.BigDecimal;

import skamila.calculator.api.exceptions.DivByZeroException;

public class Division implements Operation {

    @Override
    public BigDecimal doOperation(BigDecimal a, BigDecimal b) throws DivByZeroException {

        if (!b.equals(new BigDecimal("0"))) {
            return a.divide(b);
        } else {
            throw new DivByZeroException();
        }

    }

    @Override
    public String getSymbol() {
        return "÷";
    }

}
