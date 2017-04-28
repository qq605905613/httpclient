package com.unionpay.demo;

public class StringUtil {
	public static boolean isBlank(String str) {
		if (str==null || str.equals("")) {
			return true;
		}else {
			return false;
		}
	}
}
