package org.woodwhales.functionalinterface.code02Lambda;

public class Logger {

    public static void showLog(int level, String message){
        //对日志的等级进行判断,如果是1级别,则输出日志信息
        if(level==1){
            System.out.println(message);
        }
    }

}
