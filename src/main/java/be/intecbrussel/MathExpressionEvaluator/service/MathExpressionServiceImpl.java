package be.intecbrussel.MathExpressionEvaluator.service;

import be.intecbrussel.MathExpressionEvaluator.exception.InvalidExpressionException;
import be.intecbrussel.MathExpressionEvaluator.model.DoubleWithIndex;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Stream;

public class MathExpressionServiceImpl implements MathExpressionService {
    private BasicMathService basicMathService;

    {
        basicMathService = new BasicMathServiceImpl();
    }

    @Override
    public String evaluate(String expression) {
        expression = expression.replaceAll(" ", "");

        if (isInvalidExpression(expression)) {
            throw new InvalidExpressionException("Invalid Expression");
        }

        expression = evaluateBrackets(expression);
        expression = evaluateMultiplyAndDivideAndModulo(expression);
        expression = evaluateAddAndSubtract(expression);

        return expression;
    }

    private boolean isInvalidExpression(String expression) {
        long amountOfOpenBrackets = Stream.of(expression.split(""))
                .filter(character -> character.equals("("))
                .count();
        long amountOfClosedBrackets = Stream.of(expression.split(""))
                .filter(character -> character.equals(")"))
                .count();

        if (amountOfClosedBrackets != amountOfOpenBrackets) {
            return true;
        }

        return false;
    }

    private String evaluateAddAndSubtract(String expression) {
        int index;

        while ((index = getLowestMDMIndex(expression)) >= 0) {
            char operator = expression.charAt(index);
            DoubleWithIndex firstNumberAndIndex = findFirstNumberAndIndex(expression, index);
            DoubleWithIndex secondNumberAndIndex = findSecondNumberAndIndex(expression, index);
            double result = 0;

            switch (operator) {
                case '+':
                    result = basicMathService.add(firstNumberAndIndex.value, secondNumberAndIndex.value);
                    break;
                case '-':
                    result = basicMathService.subtract(firstNumberAndIndex.value, secondNumberAndIndex.value);
                    break;
            }

            expression = new StringBuilder(expression)
                    .replace(firstNumberAndIndex.index, secondNumberAndIndex.index+1, String.valueOf(result))
                    .toString();


        }

        return expression;
    }

    private String evaluateMultiplyAndDivideAndModulo(String expression) {
        int index;

        while ((index = getLowestMDMIndex(expression)) >= 0) {
            char operator = expression.charAt(index);
            DoubleWithIndex firstNumberAndIndex = findFirstNumberAndIndex(expression, index);
            DoubleWithIndex secondNumberAndIndex = findSecondNumberAndIndex(expression, index);
            double result = 0;

            switch (operator) {
                case '*':
                    result = basicMathService.multiply(firstNumberAndIndex.value, secondNumberAndIndex.value);
                    break;
                case '/':
                    result = basicMathService.divide(firstNumberAndIndex.value, secondNumberAndIndex.value);
                    break;
                case '%':
                    result = basicMathService.modulo(firstNumberAndIndex.value, secondNumberAndIndex.value);
                    break;
            }

            expression = new StringBuilder(expression)
                    .replace(firstNumberAndIndex.index, secondNumberAndIndex.index+1, String.valueOf(result))
                    .toString();


        }

        return expression;
    }

    private DoubleWithIndex findSecondNumberAndIndex(String expression, int index) {
        int firstNumberIndex = findFirstIndexOfAny(expression, index);
        if (firstNumberIndex < 0) {
            return new DoubleWithIndex(0, -1);
        }

        return new DoubleWithIndex(Double.parseDouble(expression.substring(firstNumberIndex, index)), firstNumberIndex);
    }

    private int findFirstIndexOfAny(String expression, int index) {
        String operators = "+-*/%";
        int numberIndex = -1;

        for (int i = index+1; i < expression.length(); i++) {
            char currentChar = expression.charAt(i);
            if (operators.contains(""+currentChar)) {
                numberIndex = i-1; // i is operator, i+1 is first digit
                break;
            }
        }

        return numberIndex;
    }

    private DoubleWithIndex findFirstNumberAndIndex(String expression, int index) {
        int firstNumberIndex = getLastIndexOfAny(expression, index);
        if (firstNumberIndex < 0) {
            return new DoubleWithIndex(0, -1);
        }

        return new DoubleWithIndex(Double.parseDouble(expression.substring(firstNumberIndex, index)), firstNumberIndex);
    }

    private int getLastIndexOfAny(String expression, int endIndex) {
        String operators = "+-*/%";
        int numberIndex = -1;

        for (int i = endIndex; i >= 0; i--) {
            char currentChar = expression.charAt(i);
            if (operators.contains(""+currentChar)) {
                numberIndex = i+1; // i is operator, i+1 is first digit
                break;
            }
        }

        return numberIndex;
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

    private int findCloseBracketIndex(String expression, int indexOpenBracket) {
        int counter = 0;
        for (int i = indexOpenBracket+1; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') counter++;
            else if (expression.charAt(i) == ')') counter--;

            if (counter == -1) return i;
        }

        return -1;
    }
}
