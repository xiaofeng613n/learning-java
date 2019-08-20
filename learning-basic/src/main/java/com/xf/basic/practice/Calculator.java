package com.xf.basic.practice;

import java.util.Stack;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-19 15:55
 * @Description:
 */
public class Calculator {

    private Stack<Character> operatorStack = new Stack<>();
    private Stack<Integer> operandStack = new Stack<>();

    public Integer cal(String expression) {
        int length = expression.length();
        for (int i = 0; i < length; i ++) {
            char c = expression.charAt(i);
            if( c >= '0' && c <= '9') {
                operandStack.push(Integer.valueOf(String.valueOf(c)));
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while ( !operatorStack.isEmpty()) {
                    if (comparePriority(c, operatorStack.peek())) {
                        operatorStack.push(c);
                        break;
                    }else {
                        char operator = operatorStack.pop();
                        Integer operand2 = operandStack.pop();
                        Integer operand1 = operandStack.pop();
                        int result = cal(operand1, operand2, operator);
                        operandStack.push(result);
                    }
                }

                if ( operatorStack.isEmpty()) {
                    operatorStack.push(c);
                }
            }
        }

        char operator = operatorStack.pop();
        Integer operand2 = operandStack.pop();
        Integer operand1 = operandStack.pop();
        int result = cal(operand1, operand2, operator);
        return result;
    }

    private boolean comparePriority(char operator1, char operator2) {
        if ( (operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-') ) {
            return true;
        }
        return false;
    }

    private int cal (int operand1, int operand2, char operator) {
        int result = 0;
        switch (operator) {
            case '+': result = operand1 + operand2;
                break;
            case '-': result = operand1 - operand2;
                break;
            case '*': result = operand1 * operand2;
                break;
            case '/': result = operand1 / operand2;
                break;
            default:
                break;
        }
        return result;
    }


    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        String expression = "1*7+2*3-1";
        int result = calculator.cal(expression);
        System.out.println(result);

        String expression2 = "1*7";
        result = calculator.cal(expression2);
        System.out.println(result);
    }


}
