package skamila.calculator.api.fast_operation;

import java.math.BigDecimal;

import skamila.calculator.api.exceptions.NegativeNumberException;

public class NaturalLogarithm implements FastOperation {

    @Override
    public BigDecimal doOperation(BigDecimal a) throws NegativeNumberException {

        if (a.doubleValue() >= 0) {
            return BigDecimal.valueOf(Math.log(a.doubleValue()));
        } else {
            throw new NegativeNumberException();
        }

    }

}
