package be.intecbrussel.MathExpressionEvaluator.service;

import java.math.BigDecimal;

public class BasicMathServiceImpl implements BasicMathService{
    @Override
    public double add(double firstNumber, double secondNumber) {
        String firstNumberAsString = String.valueOf(firstNumber);
        String secondNumberAsString = String.valueOf(secondNumber);

        BigDecimal firstDecimal = new BigDecimal(firstNumberAsString);
        BigDecimal secondDecimal = new BigDecimal(secondNumberAsString);

        BigDecimal result = firstDecimal.add(secondDecimal);

        return result.doubleValue();
    }

    @Override
    public double subtract(double firstNumber, double secondNumber) {
        return 0;
    }

    @Override
    public double multiply(double firstNumber, double secondNumber) {
        return 0;
    }

    @Override
    public double divide(double dividend, double divider) {
        return 0;
    }
}
