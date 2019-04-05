package skamila.calculator.api.fast_operation;

import java.math.BigDecimal;

public class Cosine implements FastOperation {

    @Override
    public BigDecimal doOperation(BigDecimal a) {
        return BigDecimal.valueOf(Math.cos(a.doubleValue() / 57.2957795));
    }

}
