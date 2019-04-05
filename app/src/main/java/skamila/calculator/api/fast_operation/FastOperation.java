package skamila.calculator.api.fast_operation;

import java.math.BigDecimal;

import skamila.calculator.api.exceptions.NegativeNumberException;

public interface FastOperation {

    BigDecimal doOperation(BigDecimal a) throws NegativeNumberException;

}
