package be.intecbrussel.MathExpressionEvaluator.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
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

    @ParameterizedTest
    @MethodSource("basicSubtractionFactory")
    public void testBasicSubtraction(double number1, double number2, double expectedValue) {
        double result = basicMathService.subtract(number1, number2);
        Assertions.assertEquals(expectedValue, result);
    }

    public static Stream<Arguments> basicSubtractionFactory() {
        return Stream.of(
                Arguments.of(5, 3, 2),
                Arguments.of(50, 3, 47),
                Arguments.of(-5, 3, -8),
                Arguments.of(0, 0, 0),
                Arguments.of(-7, -3, -4),
                Arguments.of(5, -3, 8),
                Arguments.of(5.5, 4.5, 1),
                Arguments.of(2000000000, 2000000000, 0),
                Arguments.of(-0.00001, 0.00002, -0.00003),
                Arguments.of(0.99999, 0.000001, 0.999989),
                Arguments.of(-0.99999, 0.00001, -1)
        );
    }

    @ParameterizedTest
    @MethodSource("basicMultiplicationFactory")
    public void testBasicMultiplications(double number1, double number2, double expectedValue) {
        double result = basicMathService.multiply(number1, number2);
        Assertions.assertEquals(expectedValue, result);
    }

    public static Stream<Arguments> basicMultiplicationFactory() {
        return Stream.of(
                Arguments.of(5, 3, 15),
                Arguments.of(50, 3, 150),
                Arguments.of(-5, 3, -15),
                Arguments.of(0, 0, 0),
                Arguments.of(0, 5, 0),
                Arguments.of(3, 0, 0),
                Arguments.of(-7, -3, 21),
                Arguments.of(5, -3, -15),
                Arguments.of(5.5, 4.5, 24.75),
                Arguments.of(2000000000, 2000000000, 4000000000000000000L),
                Arguments.of(-0.00001, 0.00002, -0.0000000002)
        );
    }

    @ParameterizedTest
    @MethodSource("basicDivisionFactory")
    public void testBasicDivision(double number1, double number2, double expectedValue) {
        double result = basicMathService.divide(number1, number2);
        Assertions.assertEquals(expectedValue, result);
    }

    public static Stream<Arguments> basicDivisionFactory() {
        return Stream.of(
                Arguments.of(9, 3, 3),
                Arguments.of(-5, 5, -1),
                Arguments.of(0, 5, 0),
                Arguments.of(10, -1, -10),
                Arguments.of(6, -3, -2),
                Arguments.of(1, 3, 0.3333333333),
                Arguments.of(5.5, 4.5, 1.2222222222),
                Arguments.of(2000000000, 2000000000, 1),
                Arguments.of(0.00001, 0.00002, 0.5)
                );
    }

    @ParameterizedTest
    @MethodSource("basicDivisionExceptionFactory")
    public void testBasicDivision_Exceptions(double number1, double number2, Class<Exception> expectedException) {
        Assertions.assertThrows(expectedException,
                () -> basicMathService.divide(number1, number2));
    }

    public static Stream<Arguments> basicDivisionExceptionFactory() {
        return Stream.of(
                Arguments.of(0, 0, ArithmeticException.class),
                Arguments.of(5, 0, ArithmeticException.class),
                Arguments.of(-5, 0, ArithmeticException.class)
        );
    }

    @ParameterizedTest
    @MethodSource("basicModuloFactory")
    public void testBasicModulo(double number1, double number2, double expectedValue) {
        double result = basicMathService.modulo(number1, number2);
        Assertions.assertEquals(expectedValue, result);
    }

    public static Stream<Arguments> basicModuloFactory() {
        return Stream.of(
                Arguments.of(9, 3, 0),
                Arguments.of(0, 5, 0)
        );
    }
}
