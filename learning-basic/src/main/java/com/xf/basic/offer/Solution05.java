package com.xf.basic.offer;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-18 11:41
 * @Description:
 */
public class Solution05 {

    //原题考察了C++中指针的操作，从后向前遍历字符串，但是Java里数组不可变

    public static String replaceBlank(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if ( str.charAt(i) == ' ') {
                sb.append("%20");
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String str = "We are happr.";
        System.out.println(replaceBlank(str));
    }
}
