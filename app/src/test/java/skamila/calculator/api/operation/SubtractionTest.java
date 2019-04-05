package skamila.calculator.api.operation;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.math.BigDecimal;

import skamila.calculator.api.exceptions.DivByZeroException;
import skamila.calculator.api.exceptions.NegativeNumberException;

import static org.junit.Assert.assertThat;

public class SubtractionTest {

    @Test
    public void subtraction() throws DivByZeroException, NegativeNumberException {

        Operation operation = new Subtraction();
        BigDecimal a = new BigDecimal("8.22");
        BigDecimal b = new BigDecimal("2");

        BigDecimal result = operation.doOperation(a, b);

        assertThat(result, Is.is(new BigDecimal("6.22")));

    }

}
