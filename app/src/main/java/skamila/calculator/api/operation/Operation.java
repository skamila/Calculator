package skamila.calculator.api.operation;

import java.math.BigDecimal;

public interface Operation {

    BigDecimal doOperation(BigDecimal a, BigDecimal b);

}
