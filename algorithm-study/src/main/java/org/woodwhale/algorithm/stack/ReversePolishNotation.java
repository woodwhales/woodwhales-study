package org.woodwhale.algorithm.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰表示法（Reverse Polish notation，RPN，或逆波兰记法）， 是一种是由波兰数学家扬·武卡谢维奇1920年引入的数学表达式方式，
 * 在逆波兰记法中，所有操作符置于操作数的后面，因此也被称为后缀表示法。 逆波兰记法不需要括号来标识操作符的优先级。
 * 逆波兰结构由弗里德里希·鲍尔（Friedrich L. Bauer）和艾兹格·迪科斯彻在1960年代早期提议用于表达式求值，
 * 以利用堆栈结构减少计算机内存访问。 逆波兰记法和相应的算法由澳大利亚哲学家、计算机学家查尔斯·汉布林（Charles
 * Hamblin）在1960年代中期扩充 在1960和1970年代，逆波兰记法广泛地被用于台式计算器，因此也在普通公众（工程、商业和金融领域）中使用。
 * 
 * 	中缀表达式转换为后缀表达式步骤 ：
 *	(1) 初始化两个栈：运算符栈S1和储存中间结果的栈S2； 
 *	(2) 从左至右扫描中缀表达式；
 *	(3) 遇到操作数时，将其压入S2；
 *	(4) 遇到运算符时，比较其与S1栈顶运算符的优先级：
 *		(4.1)如果S1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈； 
 *		(4.2)否则，若优先级比栈顶运算符的高，也将运算符压入S1（注意转换为前缀表达式时是优先级较高或相同，而这里则不包括相同的情况）；
 *		(4.3) 否则，将S1栈顶的运算符弹出并压入到S2中，再次转到(4-1)与S1中新的栈顶运算符相比较；
 *	(5)  遇到括号时：
 *		(5.1) 如果是左括号“(”，则直接压入S1； 
 *		(5.2)  如果是右括号“)”，则依次弹出S1栈顶的运算符，并压入S2，直到遇到左括号为止，此时将这一对括号丢弃；
 * 	(6) 重复步骤(2)至(5)，直到表达式的最右边；
 * 	(7) 将S1中剩余的运算符依次弹出并压入S2； 
 * 	(8)依次弹出S2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式（转换为前缀表达式时不用逆序）。
 *
 */
public class ReversePolishNotation {
	public static void main(String[] args) {
		String exp = "3+(4*5-6)-16";
		/**
		 * 	中缀表达式转后缀表达式
		 * 	首先将表达式转成list，因为其中会有多位数的字符，还有连续的括号，
		 * 	所以先转成list更方便后续操作
		 * 	3+4*(5-6)-16 --> [3, + , 4, *, (, 5, -, 6, ), -, 16]
		 */
		
		// 得到中缀表达式的list
		List<String> list = getInfixList(exp);
		System.out.println("中缀表达式 -> " + list);
		// 得到后缀表达式的list
		List<String> suffixList = getSuffixList(list);
		System.out.println("后缀表达式 -> " + suffixList);
//		String parsedStr = parseExpression(exp);
//		int result2 = calculte(suffixList);
//		System.out.printf("%s = %d", exp, result2);
		
		// 计算后缀表达式
//		String expression = "3 4 + 5 * 6 -";
//		int result = calculte(expression);
//		System.out.printf("%s = %d", expression, result);
	}

	
	/**
	 * 	 将中缀表达式转为list
	 * @param exp 中缀表达式
	 * @return
	 */
	public static List<String> getInfixList(String exp) {
		List<String> result = new ArrayList<>();
		int index = 0;
		System.out.println("lenght -> "+ exp.length());
		while(index < exp.length()) {
			char item = exp.charAt(index);
			
			// 无论是运算符还是数字，都转成字符串
			String itemStr = "" + item;
			
			// 如果当前字符是数字，就检查要不要拼接
			if(itemStr.matches("\\d")) {
				// 检查当前数字字符的下一个位置不是最后一个字符位置之后并且下一个位置的字符是数字，就要拼接
				// 将拼好的字符还是存到 itemStr 中
				while((index+1) < exp.length() && (""+exp.charAt(index+1)).matches("\\d")) {
					// 注意一定要使用++index，先自增再获取下一个位置所表示的字符
					itemStr += exp.charAt(++index);
				}
			}
			
			result.add(itemStr);
			index++;
		}
		return result;
	}

	public static List<String> getSuffixList(List<String> list) {
		// 因为结果栈没有弹栈操作，所以使用 List 来代替栈结构，也方便结果栈的逆序输出
		List<String> result = new ArrayList<>();
		Stack<String> stack = new Stack<>(); 

		for (String item : list) {
			// 如果是数字，直接放入结果栈中
			if(item.matches("\\d+")) {
				result.add(item);
			} else if("(".equals(item)) {
				stack.push(item);
			} else if(")".equals(item)) {
				while(!("(".equals(stack.peek()))) {
					result.add(stack.pop());
				}
				// 由于这个分支能进来的前提一定是运算符栈里有了“（”符号，所以将"（"弹出
				stack.pop();
			} else {
				while(stack.size()!=0 && OperationHeight.getValue(stack.peek()) >= OperationHeight.getValue(item)) {
					result.add(stack.pop());
				}
				stack.push(item);
			}
		}
		
		// 将运算符栈中的剩余操作符依次压入结果栈中
		while(stack.size() != 0) {
			result.add(stack.pop());
		}
		
		return result;
	}

	/**
	 *	对后缀表达式进行计算
	 * @param expression 后缀表达式
	 * @return 后缀表达式计算的结果
	 */
	public static int calculte(String expression) {
		List<String> list = getList(expression);
		return calculte(list);
	}
	
	/**
	 * 	对后缀表达式进行计算
	 * @param list 后缀表达式的list
	 * @return 后缀表达式计算的结果
	 */
	public static int calculte(List<String> list) {
		Stack<Integer> stack = new Stack<>(); 
		for (String item : list) {
			// 正则表达式判断字符是不是纯数字字符串
			if(item.matches("\\d+")) {
				stack.push(Integer.parseInt(item));
			} else {
				cal(stack, item);
			}
		}
		
		return stack.pop();
	}

	/**
	 * 	从栈中取出两个数字进程计算
	 * @param stack	数据栈
	 * @param item 运算符
	 */
	private static void cal(Stack<Integer> stack, String item) {
		// 注意这里的两值计算顺序，先弹出栈的是运算符的后面，后弹出栈的是在运算符前面
		Integer num2 = stack.pop();
		Integer num1 = stack.pop();
		int res = 0;
		
		switch (item) {
		case "+":
			res = num1 + num2;
			break;
		case "-":
			res = num1 - num2;
			break;
		case "*":
			res = num1 * num2;
			break;
		case "/":
			res = num1 / num2;
			break;
		default:
			throw new RuntimeException("运算符号非法");
		}
		
		stack.push(res);
	}

	/**
	 * 	将后缀表达式转化成字符数组
	 * @param expression 后缀表达式
	 * @return	后缀表达式的字符列表
	 */
	private static List<String> getList(String expression) {
		String[] listArray = expression.split(" ");

		List<String> list = new ArrayList<>();
		for (String item : listArray) {
			list.add(item);
		}
		return list;
	}
}

class OperationHeight {
	private static int ADD = 1;
	private static int SUB = 1;
	private static int MUL = 2;
	private static int DIV = 2;
	
	public static int getValue(String operation) {
		int result = 0;
		switch (operation) {
		case "+":
			result = ADD;
			break;
		case "-":
			result = SUB;
			break;
		case "*":
			result = MUL;
			break;
		case "/":
			result = DIV;
			break;
		default:
			System.out.println("运算符非法！");
			break;
		}
		
		return result;
	}
}