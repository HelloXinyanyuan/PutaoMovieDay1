package com.example;

/**
 * Created by Administrator on 2016/6/21.
 * 单例模式分两种模式：
 * 懒汉式和饿汉式
 * 区别创建对象的时机
 */
public class Liudehua {
//    private static Liudehua sInstance;
    private static Liudehua sInstance=new Liudehua();//饿汉式
    private Liudehua(){//私有构造方法，防止外面创建
    }
    public  static Liudehua  getInstance(){
        if (sInstance==null){
            sInstance=new Liudehua();//懒汉式
        }
        return  sInstance;
    }

    public void sayHello(){
        System.out.println("Liudehua.sayHello"+this);
    }

}
