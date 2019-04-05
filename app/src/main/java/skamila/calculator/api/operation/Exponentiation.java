package skamila.calculator.api.operation;

import java.math.BigDecimal;

public class Exponentiation implements Operation {

    @Override
    public BigDecimal doOperation(BigDecimal a, BigDecimal b) {
        return a.pow(b.intValue());
    }

}
