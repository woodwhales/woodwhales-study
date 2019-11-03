package org.woodwhales.functionalinterface.code02Lambda;

@FunctionalInterface
public interface MessageBuilder {

    //定义一个拼接消息的抽象方法,返回被拼接的消息
    String builderMessage();

}