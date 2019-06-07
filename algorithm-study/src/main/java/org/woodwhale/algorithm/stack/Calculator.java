package org.woodwhale.algorithm.stack;

public class Calculator {

	public static void main(String[] args) {
		String expression = "1+2*8-7+9*322";
		
		int result = calculate(expression);
		System.out.printf("%s = %d\n", expression, result);
	}

	private static int calculate(String expression) {
		LinkedListStack numStack = new LinkedListStack();
		LinkedListStack operStack = new LinkedListStack();
		
		int length = expression.length();
		System.out.println(expression + " 的长度为 "+ length);
		
		for(int index = 0 ; index < length ; index++) {
			// 扫描到当前的字符
			char val = expression.charAt(index);
			// 是运算符号存入数栈
			if(isOper(val)){ 
				// 入栈之前看一下当前要压栈的运算符是不是比当前栈里面的运算符号操作级别高
				if(!operStack.isEmpty() && priority(val) <= priority(operStack.peek())) {
					operCal(numStack, operStack);
				}
				
				operStack.push(val);
				
			} else { // 是数字存入数栈
				
				String keepNum = "" + val;
				
				// 看一下下一位字符是不是数字，是数字就拼接成数字字符串
				char tempChar = expression.charAt(index+1);
				while(!isOper(tempChar)) {
					
					// 防止当前数字已经是最后一个位置了
					if(index+1 == length) {
						break;
					}
					
					tempChar = expression.charAt(++index);
					keepNum += tempChar;
				}
				numStack.push(Integer.parseInt(keepNum));
			}
			
		}
		
		// 将剩余操作符栈里的操作符取出，依次计算
		while(!operStack.isEmpty()) {
			operCal(numStack, operStack);
		}
		
//		System.out.println("当前数据栈的长度为：" + numStack.getLength());
//		System.out.println("当前操作符栈的长度为：" + operStack.getLength());
		// 最后数据栈一定只剩下一个数据，直接弹栈出来就是结果
		return numStack.pop();
	}

	/**
	 *	讲两个数据进行计算：
	 * 	从操作栈中取出操作符，再从数据栈中取出两个操作符进行计算
	 * 	将计算后的结果压入数据栈中
	 * @param numStack
	 * @param operStack
	 */
	private static void operCal(LinkedListStack numStack, LinkedListStack operStack) {
		int num1 = numStack.pop();
		int num2 = numStack.pop();
		int res = opre(num1, num2, operStack.pop());
		numStack.push(res);
	}
	
	/**
	 *	将两个数据进行四则运算
	 * @param num1	运算符之后的数值（数据栈后入栈的那个数值）
	 * @param num2	运算符之前的数值（数据栈先入栈的那个数值）
	 * @param oper	运算符
	 * @return
	 */
	private static int opre(int num1, int num2, int oper) {
		int result = 0;
		
		/**
		 *	注意一定是 num2 在前，num1在后
		 */
		switch (oper) {
		case ('+'):
			result = num2 + num1;
			break;
		case ('-'):
			result = num2 - num1;
			break;
		case ('*'):
			result = num2 * num1;
			break;
		case ('/'):
			result = num2 / num1;
			break;
		default:
			throw new RuntimeException("计算数据操作失败！");
		}
		
		return result;
	}

	private static boolean isOper(char val) {
		return '+' == val || '-' == val || '*' == val || '/' == val;
	}
	
	private static int priority(int oper) {
		int priority = -1;
		switch (oper) {
		case ('+'):
			priority = 0;
			break;
		case ('-'):
			priority = 0;
			break;
		case ('*'):
			priority = 1;
			break;
		case ('/'):
			priority = 1;
			break;
		default:
			break;
		}
		
		return priority;
	}
}
