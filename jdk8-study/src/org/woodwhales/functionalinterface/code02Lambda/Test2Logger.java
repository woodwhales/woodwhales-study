package org.woodwhales.functionalinterface.code02Lambda;

/**
 * 日志案例
 *
 *     发现以下代码存在的一些性能浪费的问题
 *     调用showLog方法,传递的第二个参数是一个拼接后的字符串
 *     先把字符串拼接好,然后在调用showLog方法
 *     showLog方法中如果传递的日志等级不是1级
 *     那么就不会是如此拼接后的字符串
 *     所以感觉字符串就白拼接了,存在了浪费
 */
public class Test2Logger {

    public static void main(String[] args) {
        //定义三个日志信息
        String msg1 = "Hello";
        String msg2 = "World";
        String msg3 = "Java";

        //调用showLog方法,传递日志级别和日志信息
        Logger.showLog(2,msg1+msg2+msg3);
    }
}
