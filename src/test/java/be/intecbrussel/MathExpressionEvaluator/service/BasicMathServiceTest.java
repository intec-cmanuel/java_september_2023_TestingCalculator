package be.intecbrussel.MathExpressionEvaluator.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class BasicMathServiceTest {
    private final BasicMathService basicMathService;

    {
        this.basicMathService = new BasicMathServiceImpl();
    }

    @Test
    public void testBasicAdditionOfTwoIntegers() {
        int firstNumber = 7;
        int secondNumber = 6;

        int expectedValue = 13;

        double result = basicMathService.add(firstNumber, secondNumber);

        Assertions.assertEquals(expectedValue, result);
    }

    @Test
    public void testBasicAdditionOfTwoNegativeIntegers() {
        int firstNumber = -4;
        int secondNumber= -8;
        int expectedResult = -12;

        double result = basicMathService.add(firstNumber, secondNumber);

        Assertions.assertEquals(expectedResult, result);

    }

    @ParameterizedTest
    @MethodSource("basicAdditionFactory")
    public void testBasicAdditions(double number1, double number2, double expectedValue) {
        double result = basicMathService.add(number1, number2);
        Assertions.assertEquals(expectedValue, result);
    }

    public static Stream<Arguments> basicAdditionFactory() {
        return Stream.of(
                Arguments.of(5, 3, 8),
                Arguments.of(50, 3, 53),
                Arguments.of(-5, 3, -2),
                Arguments.of(0, 0, 0),
                Arguments.of(-7, -3, -10),
                Arguments.of(5, -3, 2),
                Arguments.of(5.5, 4.5, 10),
                Arguments.of(2000000000, 2000000000, 4000000000L),
                Arguments.of(-0.00001, 0.00002, 0.00001),
                Arguments.of(0.99999, 0.000001, 0.999991)
        );
    }
}
