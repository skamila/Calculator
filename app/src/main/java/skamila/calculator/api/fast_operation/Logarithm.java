package skamila.calculator.api.fast_operation;

import java.math.BigDecimal;

public class Logarithm implements FastOperation {

    @Override
    public BigDecimal doOperation(BigDecimal a) {
        return BigDecimal.valueOf(Math.log10(a.doubleValue()));
    }

}
