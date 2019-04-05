package skamila.calculator.api.operation;

import java.math.BigDecimal;

public class Subtraction implements Operation {

    @Override
    public BigDecimal doOperation(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

}
