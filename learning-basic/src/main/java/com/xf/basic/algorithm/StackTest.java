package com.xf.basic.algorithm;

import java.util.Stack;

/**
 * Created by xiao on 2018/11/18.
 */
public class StackTest {

	private Stack<Integer> stack;

	private StringBuilder output;
	private Stack<Character> operatorStack;

	public StackTest() {
		stack = new Stack<>();
		output = new StringBuilder();
		operatorStack = new Stack<>();
	}

	private int calPosfix(String expr) {

		for (int i = 0; i < expr.length(); i++) {
			char c = expr.charAt(i);
			if ( isNumber(c) ) {
				stack.push( Integer.valueOf(c) );
			} else if ( isOperator(c)) {
				int operand1 = stack.pop();
				int operand2 = stack.pop();
				int r = cal(c, operand1, operand2);
				stack.push(r);
			}
		}
		return stack.pop();
	}

	private boolean isOperator(char c) {
		if (c >= 42 && c <= 45) {
			return true;
		}
		return false;
	}

	private boolean isNumber(char c) {
		if ( c >= 48 && c <= 57 ) {
			return true;
		}
		return false;
	}

	private int cal(char operator, int operand1, int operand2) {
		int result = 0;
		switch (operator) {
			case '+':
				result = operand1 + operand2;
				break;
			case '-':
				result = operand1 - operand2;
				break;
			case '*':
				result = operand1 * operand2;
				break;
			case '/':
				result = operand1 / operand2;
				break;
		}
		return result;
	}

	private String infixToPosfix(String expr) {
		System.out.println("infix:" + expr);
		for (int i = 0; i < expr.length(); i ++) {
			char c = expr.charAt(i);
			if ( isNumber(c) ) {
				output.append(c);
			} else if ( isOperator(c) || c == '(') {
				int currentPriority = getPriority(c);
				if ( !operatorStack.isEmpty() ) {
					char out = operatorStack.peek();
					while ( out != '(' && getPriority(out) >=  currentPriority ) {
						output.append(operatorStack.pop());
						out = operatorStack.peek();
					}
				}
				operatorStack.push(c);
			} else if( c == ')')  {
				char out = operatorStack.pop();
				do {
					output.append(out);
					out = operatorStack.pop();
				} while ( out != '(' );
			}
		}
		return output.toString();
	}

	private int getPriority(char c ) {
		return 0;
	}

	public int calExpression(String expr) {
		String posfix = infixToPosfix(expr);
		return calPosfix(posfix);
	}

	public static void main(String[] args) {
		String expr = "";
		StackTest calculator = new StackTest();
		System.out.println("result:" + calculator.calExpression(expr));
	}

}
