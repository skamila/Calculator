package skamila.calculator.api.fast_operation;

import java.math.BigDecimal;

public class ChangeSignOperation implements FastOperation {

    @Override
    public BigDecimal doOperation(BigDecimal a) {
        return a.multiply(new BigDecimal("-1"));
    }

}
