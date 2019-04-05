package skamila.calculator.api.fast_operation;

import java.math.BigDecimal;

public class ToThePowerTwo implements FastOperation {

    @Override
    public BigDecimal doOperation(BigDecimal a) {
        return a.pow(2);
    }

}
