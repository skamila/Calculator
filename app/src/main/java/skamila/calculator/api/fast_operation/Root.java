package skamila.calculator.api.fast_operation;

import java.math.BigDecimal;

public class Root implements FastOperation {

    @Override
    public BigDecimal doOperation(BigDecimal a) {
        return BigDecimal.valueOf(Math.sqrt(a.doubleValue()));
    }

}
