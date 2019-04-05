package skamila.calculator.api.operation;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertThat;

public class MultiplyTest {

    @Test
    public void multiply() {

        Operation operation = new Multiply();
        BigDecimal a = new BigDecimal("8.22");
        BigDecimal b = new BigDecimal("2");

        BigDecimal result = operation.doOperation(a, b);

        assertThat(result, Is.is(new BigDecimal("16.44")));

    }

}
