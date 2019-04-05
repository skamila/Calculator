package skamila.calculator.api.operation;

import java.math.BigDecimal;

public class Multiply implements Operation {

    @Override
    public BigDecimal doOperation(BigDecimal a, BigDecimal b) {
        return a.multiply(b);
    }

}
