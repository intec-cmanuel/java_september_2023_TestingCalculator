package be.intecbrussel.MathExpressionEvaluator.service;

import be.intecbrussel.MathExpressionEvaluator.exception.InvalidExpressionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class MathExpressionServiceTest {
    private MathExpressionService mathExpressionService;

    {
        mathExpressionService = new MathExpressionServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("expressionFactory")
    public void testExpression(String expression, String expectedResult) {
        String result = mathExpressionService.evaluate(expression);
        Assertions.assertEquals(expectedResult, result);
    }

    public static Stream<Arguments> expressionFactory() {
        return Stream.of(
                Arguments.of("4 + 2", "6"),
                Arguments.of("-4 + 2", "-2"),
                Arguments.of("2--4", "6"),
                Arguments.of("-4-2", "-6"),
                Arguments.of("1 + 0.1", "1.1"),
                Arguments.of("0.99999 + 0.000001", "0.999991"),
                Arguments.of("10/90 + 80/90", "1"),
                Arguments.of("10 * (1 + (5 - 1) + 5)", "100"),
                Arguments.of("-5", "-5"),
                Arguments.of("0", "0"),
                Arguments.of("5 - 0.5", "4.5"),
                Arguments.of("0.5 + 1", "1.5"),
                Arguments.of("6 / -2", "-3"),
                Arguments.of("50 % 3", "2"),
                Arguments.of("1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 - 1 - 1 - 1 - 1 - 1 - 1 - 1 * 2 * 2 * 2 * 4 / 4", "-16"),
                Arguments.of("100 / (1 / 10000000000000000000)", "1e+21"),
                Arguments.of("6---8", "-2"),
                Arguments.of("999999 * 999999 * 999999", "9.99997e+17"),
                Arguments.of("10 / (-2 - 2)", "-2.5"),
                Arguments.of("99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999 * 2", "2e+119"),
                Arguments.of("2 / 99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999", "2e-119")

        );
    }

    @ParameterizedTest
    @MethodSource("expressionExceptionFactory")
    public void testExpression_Exception(String expression, Class<Exception> expectedException) {
        Assertions.assertThrows(expectedException,
                () -> mathExpressionService.evaluate(expression));
    }

    public static Stream<Arguments> expressionExceptionFactory() {
        return Stream.of(
                Arguments.of("3/0", InvalidExpressionException.class),
                Arguments.of("potato salad", InvalidExpressionException.class),
                Arguments.of("x + y", InvalidExpressionException.class),
                Arguments.of("sqrt(9)", InvalidExpressionException.class),
                Arguments.of("5^2", InvalidExpressionException.class),
                Arguments.of("10.3.5 - 5", InvalidExpressionException.class)
                );
    }
}





