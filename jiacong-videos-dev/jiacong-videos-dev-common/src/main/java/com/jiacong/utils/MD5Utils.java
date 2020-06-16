package com.jiacong.utils;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

public class MD5Utils {

	/**
	 * @Description: 此为MD5加密工具类,对字符串进行md5加密
	 */
	public static String getMD5Str(String strValue) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
		return newstr;
	}

	//测试方法
	public static void main(String[] args) {
		try {
			String md5 = getMD5Str("jiacong");
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
