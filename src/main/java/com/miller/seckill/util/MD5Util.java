package com.miller.seckill.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by miller on 2018/8/9
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String SALT = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + SALT.charAt(0) + SALT.charAt(2) + inputPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass,String salt) {
        String str = SALT.charAt(0) + SALT.charAt(2) + formPass + SALT.charAt(5) + SALT.charAt(4);
        return md5(str);
    }

    public static void main(String[] args) {
        //dd3691fd927f34ce2de9a6e5246cfc6f
        System.out.println(formPassToDBPass("dd3691fd927f34ce2de9a6e5246cfc6f","1a2b3c4d5e"));
    }
}
