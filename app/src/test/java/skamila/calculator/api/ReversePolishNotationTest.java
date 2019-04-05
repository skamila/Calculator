package skamila.calculator.api;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.math.BigDecimal;

import skamila.calculator.api.exceptions.DivByZeroException;
import skamila.calculator.api.exceptions.NegativeNumberException;
import skamila.calculator.api.operation.Addition;
import skamila.calculator.api.operation.Multiply;

import static org.junit.Assert.assertThat;

public class ReversePolishNotationTest {

    @Test
    public void test() throws DivByZeroException, NegativeNumberException {

        ReversePolishNotation reversePolishNotation = new ReversePolishNotation();
        reversePolishNotation.putNumber(new BigDecimal("4"));
        reversePolishNotation.putNumber(new BigDecimal("5"));
        reversePolishNotation.doOperation(new Multiply());
        reversePolishNotation.putNumber(new BigDecimal("5"));

        BigDecimal result = reversePolishNotation.doOperation(new Addition());

        assertThat(result, Is.is(new BigDecimal("25")));

    }

}
