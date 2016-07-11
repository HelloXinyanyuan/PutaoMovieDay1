package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("MyClass.main");
//        lixiaohuideMacBook-Pro:Android Studio.app xiaohui$ keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
//        别名: androiddebugkey
//        创建日期: 2016-7-2
//        条目类型: PrivateKeyEntry
//        证书链长度: 1
//        证书[1]:
//        所有者: C=US, O=Android, CN=Android Debug
//        发布者: C=US, O=Android, CN=Android Deb  ug
//        序列号: 1
//        有效期开始日期: Sat Jul 02 10:25:14 CST 2016, 截止日期: Mon Jun 25 10:25:14 CST 2046
//        证书指纹:
//        MD5: CB:83:FA:65:5F:60:AB:B1:0E:70:A6:B2:9B:66:69:B1
//        SHA1: D7:0E:CB:16:52:4B:92:72:36:E4:99:E0:5C:EC:6C:26:1D:C1:17:B7
//        SHA256: AD:B3:32:BD:6D:A2:D0:BB:C9:5F:27:81:A5:50:D0:DB:DA:22:72:1D:5C:B5:C9:75:B1:EE:25:D7:FD:6A:B7:B7
//        签名算法名称: SHA1withRSA
//        版本: 1
//        lixiaohuideMacBook-Pro:Android Studio.app xiaohui$  cd ~/

//        new Liudehua().sayHello();
//        Liudehua.getInstance().sayHello();
//        Liudehua.getInstance().sayHello();
//        Liudehua.getInstance().sayHello();
//        Liudehua.getInstance().sayHello();
//        Liudehua.getInstance().sayHello();
        String file = "/Users/xiaohui/.android/debug.keystore";
        InputStream is= null;
        try {
            is = new FileInputStream(new File(file));
            byte[] arr=new byte[is.available()];

            is.read(arr);

            System.out.println(new MD5(null).compute(arr));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}