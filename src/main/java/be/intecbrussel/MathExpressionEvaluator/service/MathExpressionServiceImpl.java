package be.intecbrussel.MathExpressionEvaluator.service;

import be.intecbrussel.MathExpressionEvaluator.exception.InvalidExpressionException;

import java.util.Arrays;
import java.util.OptionalInt;

public class MathExpressionServiceImpl implements MathExpressionService {
    private BasicMathService basicMathService;

    {
        basicMathService = new BasicMathServiceImpl();
    }

    @Override
    public String evaluate(String expression) {
        if (isInvalidExpression()) {
            throw new InvalidExpressionException("Invalid Expression");
        }

        expression = evaluateBrackets(expression);
        expression = evaluateMultiplyAndDivideAndModulo(expression);
        expression = evaluateAddAndSubtract(expression);

        return expression;
    }

    private String evaluateMultiplyAndDivideAndModulo(String expression) {
        int index;

        while ((index = getLowestMDMIndex(expression)) >= 0) {
            char operator = expression.charAt(index);
            double firstNumber;
            double secondNumber;
            double result;

            switch (operator) {
                case '*':
                    result = basicMathService.multiply(firstNumber, secondNumber);
                    break;
                case '/':
                    result = basicMathService.divide(firstNumber, secondNumber);
                    break;
                case '%':
                    result = basicMathService.modulo(firstNumber, secondNumber);
                    break;
            }
        }
    }

    private int getLowestMDMIndex(String expression) {
        int[] indices = new int[3];
        indices[0] = expression.indexOf("*");
        indices[1] = expression.indexOf("/");
        indices[2] = expression.indexOf("%");

        OptionalInt first = Arrays.stream(indices)
                .filter(index -> index >= 0)
                .sorted()
                .findFirst();

        int index = first.orElse(-1);
        return index;
    }

    private String evaluateBrackets(String expression) {
        int indexOpenBracket;
        int indexCloseBracket;

        while ((indexOpenBracket = expression.indexOf("(")) >= 0) {

            indexCloseBracket = findCloseBracketIndex(expression, indexOpenBracket);

            if (indexOpenBracket >= 0) {
                String evaluation = evaluate(expression.substring(indexOpenBracket + 1, indexCloseBracket));
                expression = new StringBuilder(expression)
                        .replace(indexOpenBracket, indexCloseBracket + 1, evaluation)
                        .toString();
            }

        }

        return expression;
    }
}
