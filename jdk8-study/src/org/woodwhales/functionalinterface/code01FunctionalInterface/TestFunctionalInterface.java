package org.woodwhales.functionalinterface.code01FunctionalInterface;

/**
 * 认识函数式接口和Lambda表达式
 */
public class TestFunctionalInterface {

    public static void say(MyFunctionalInterface myFunctionalInterface) {
        myFunctionalInterface.sayHello();
    }

    public static void main(String[] args) {
        /**
         * 传统写法
         */
        say(new MyFunctionalInterface() {
            @Override
            public void sayHello() {
                System.out.println("hello world");
            }
        });

        // 调用say方法,方法的参数是一个函数式接口,所以可以Lambda表达式
        say(() -> {
            System.out.println("使用Lambda表达式重写接口中的抽象方法");
        });

        // 简化Lambda表达式：当实现类中只有一行代码的时候，可以简写
        say(() -> System.out.println("使用Lambda表达式重写接口中的抽象方法"));
    }
}
