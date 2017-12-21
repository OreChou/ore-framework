package org.orechou.ore.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author OreChou
 * @create 2017-12-21 19:35
 */
public class NamingStringUtils {

    private static String doCamelToUnderline(String str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String w = matcher.group().trim();
            str = str.replace(w, "_" + w.toLowerCase());
        }
        return str;
    }

    /**
     * 驼峰转下划线
     * 若首字母为驼峰大写的话，首字母转成小写
     * @param str 传入的字符串
     * @return
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.equals("")) {
            return "";
        }
        if (Character.isUpperCase(str.charAt(0))) {
            str = String.valueOf(str.charAt(0)).toLowerCase() + str.substring(1, str.length());
        }
        return doCamelToUnderline(str);
    }

    /**
     * 下划线转驼峰
     * @param str
     * @return
     */
    public static String underlineToCamel(String str) {
        Pattern pattern = Pattern.compile("[_]\\w");
        str = str.toLowerCase();
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String w = matcher.group().trim();
            str = str.replace(w, w.toUpperCase().replace("_", ""));
        }
        return str;
    }

//    public static void main(String[] args) {
//        System.out.println(camelToUnderline("AabcAbcAbc"));
//        System.out.println(underlineToCamel("aabc_abc_abc"));
//    }

}
