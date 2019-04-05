package skamila.calculator.api.operation;

import java.math.BigDecimal;

public class Addition implements Operation {

    @Override
    public BigDecimal doOperation(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    public String getSymbol() {
        return "+";
    }

}
