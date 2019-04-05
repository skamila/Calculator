package skamila.calculator.api.operation;

import java.math.BigDecimal;

import skamila.calculator.api.exceptions.DivByZeroException;
import skamila.calculator.api.exceptions.NegativeNumberException;

public interface Operation {

    BigDecimal doOperation(BigDecimal a, BigDecimal b) throws DivByZeroException, NegativeNumberException;

    String getSymbol();

}
