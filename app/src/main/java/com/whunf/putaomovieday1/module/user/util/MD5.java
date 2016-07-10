package com.whunf.putaomovieday1.module.user.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5 {
    private String inStr;
    private MessageDigest md5;

    public MD5(String inStr) {
        this.inStr = inStr;
        try {
            this.md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String compute() {
        byte[] byteArray = null;
        try {
            byteArray = this.inStr.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] md5Bytes = this.md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
    
    public static String toMD5(String s){
        MD5 md5 = new MD5(s);
        return md5.compute();
    }
}
