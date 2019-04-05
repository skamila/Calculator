package skamila.calculator.api.fast_operation;

import java.math.BigDecimal;

public class Percent implements FastOperation {

    @Override
    public BigDecimal doOperation(BigDecimal a) {
        return a.divide(new BigDecimal("100"));
    }

}
