package skamila.calculator.api.operation;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertThat;

public class AdditionTest {

    @Test
    public void addition() {

        Operation operation = new Addition();
        BigDecimal a = new BigDecimal("8.22");
        BigDecimal b = new BigDecimal("2");

        BigDecimal result = operation.doOperation(a, b);

        assertThat(result, Is.is(new BigDecimal("10.22")));

    }

}
