package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (statement == null || statement.equals("")) {
            return null;
        }

        double finalResult;
        try {
            List<String> finalSequence = getElementsSequence(statement);
            finalResult = calculateSimpleExpression(finalSequence);
        } catch (IllegalArgumentException | ArithmeticException e) {
            return null;
        }

        return getCorrectedFinalResult(finalResult);
    }

    private static String getCorrectedFinalResult(double result) {
        if (result % 1 == 0) {
            return String.valueOf((int) result);
        }

        if (String.valueOf(result).split("\\.")[1].length() > 4) {
            return String.format(Locale.US, "%.4f", result);
        }

        return String.valueOf(result);
    }

    private static double calculateSimpleExpression(List<String> elements) throws ArithmeticException {
        double transitionResult;

        for (int i = 0; i < elements.size(); ) {
            if (elements.get(i).equals("*")) {
                transitionResult = Double.parseDouble(elements.remove(i - 1)) * Double.parseDouble(elements.remove(i));
                elements.set(i - 1, String.valueOf(transitionResult));
            } else if (elements.get(i).equals("/")) {
                if (Double.parseDouble(elements.get(i + 1)) == 0) {
                    throw new ArithmeticException();
                }

                transitionResult = Double.parseDouble(elements.remove(i - 1)) / Double.parseDouble(elements.remove(i));
                elements.set(i - 1, String.valueOf(transitionResult));
            } else {
                i++;
            }
        }

        double finalResult = 0;

        for (String element : elements) {
            finalResult += Double.parseDouble(element);
        }

        return finalResult;
    }

    private static List<String> getElementsSequence(String expression) throws IllegalArgumentException, ArithmeticException {
        System.out.println("Expression: " + expression);

        List<String> elements = new ArrayList<>();

        int selectedCharsLength = 0;

        Pattern generalPattern = Pattern.compile("(\\(([^)]+)\\)+|(([+\\-])?+\\d+(\\.\\d+)?)|([*/]))");
        Matcher generalMatcher = generalPattern.matcher(expression);

        while (generalMatcher.find()) {
            String nextElement = generalMatcher.group();

            selectedCharsLength += nextElement.length();

            if (Pattern.compile("\\(([^)]+)\\)+").matcher(nextElement).find()) {
                nextElement = nextElement.substring(1, nextElement.length() - 1);

                List<String> transitionList = getElementsSequence(nextElement);

                double transitionResult = calculateSimpleExpression(transitionList);
                elements.add(String.valueOf(transitionResult));
            } else {
                elements.add(nextElement);
            }
        }

        if (expression.length() != selectedCharsLength) {
            throw new IllegalArgumentException();
        }

        return elements;
    }
}
