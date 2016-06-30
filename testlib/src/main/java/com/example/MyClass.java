package com.example;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("MyClass.main");

//        new Liudehua().sayHello();

        Liudehua.getInstance().sayHello();
        Liudehua.getInstance().sayHello();
        Liudehua.getInstance().sayHello();
        Liudehua.getInstance().sayHello();
        Liudehua.getInstance().sayHello();
    }
}