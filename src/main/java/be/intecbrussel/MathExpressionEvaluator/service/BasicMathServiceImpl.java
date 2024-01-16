package be.intecbrussel.MathExpressionEvaluator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BasicMathServiceImpl implements BasicMathService{
    @Override
    public double add(double firstNumber, double secondNumber) {
        BigDecimal firstDecimal = convertDoubleToBigDecimal(firstNumber);
        BigDecimal secondDecimal = convertDoubleToBigDecimal(secondNumber);

        BigDecimal result = firstDecimal.add(secondDecimal);

        return result.doubleValue();
    }

    @Override
    public double subtract(double firstNumber, double secondNumber) {
        BigDecimal firstDecimal = convertDoubleToBigDecimal(firstNumber);
        BigDecimal secondDecimal = convertDoubleToBigDecimal(secondNumber);

        BigDecimal result = firstDecimal.subtract(secondDecimal);

        return result.doubleValue();
    }

    @Override
    public double multiply(double firstNumber, double secondNumber) {
        BigDecimal firstDecimal = convertDoubleToBigDecimal(firstNumber);
        BigDecimal secondDecimal = convertDoubleToBigDecimal(secondNumber);

        BigDecimal result = firstDecimal.multiply(secondDecimal);

        return result.doubleValue();

    }

    @Override
    public double divide(double dividend, double divider) {
        BigDecimal firstDecimal = convertDoubleToBigDecimal(dividend);
        BigDecimal secondDecimal = convertDoubleToBigDecimal(divider);

        BigDecimal result = firstDecimal.divide(secondDecimal, 10, RoundingMode.HALF_UP);

        return result.doubleValue();

    }

    @Override
    public double modulo(double firstNumber, double secondNumber) {
        return firstNumber % secondNumber;
    }

    private BigDecimal convertDoubleToBigDecimal(double number) {
        String numberAsString = String.valueOf(number);
        return new BigDecimal(numberAsString);
    }
}
