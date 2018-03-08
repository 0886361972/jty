package com.tianyu;

/**
 * Created by hzhonglog on 17/5/16.
 */
public class test001 {
    public static void main(String[] args) {
        String str = "/ajaxMore.html?type=A&qi=1&number=3&page=1&yunsuo_session_verify=3267b95246f96f7a203ce4e95e568678&";
        str = str.substring(str.indexOf("yunsuo_session_verify"));
        str = str.substring(0, str.indexOf("&"));
        System.out.println(str);
    }
}
