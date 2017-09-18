package com.ptsoft.pts.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 工具类
 * @author zumin.yang
 * @date 2016-03-08
 */
public class Tools {
	public static final int BOTH_IN_C1_C2 = 0;
	public static final int IN_C1_NOTIN_C2 = 1;
	public static final int NOTIN_C1_IN_C2 = 2;

	public static String RandomString(int longth, String sourceChars) {
		return RandomStringUtils.random(longth, sourceChars);
	}

	public static String RandomPassword(int longth) {
		return RandomString(longth, "abcdefghijklmn0123456789opqrstuvwxyz.-/$@#");
	}

	public static String Random10digitsPassword() {
		return RandomString(10, "abcdefghijklmn0123456789opqrstuvwxyz.-/$@#");
	}
	
	public static String Random6digitsCode() {
		return RandomString(6, "1234567890");
	}
	
	public static String Random3digitsCode() {
		return RandomString(3, "1234567890");
	}
	
	public static String Random1CharCode() {
		return RandomString(1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}

	public static boolean checkEmailAddress(String email) {
		if (email == null || email.isEmpty())
			return false;
		
		String regex = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		
		if(m.matches())
			return true;
		return false;
	}
	
	public static boolean checkMobile(String mobile)
	{
		if (mobile == null || mobile.isEmpty())
			return false;
		String regex = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile);
		
		if(m.matches())
			return true;
		return false;
	}
	
	public static boolean timeOver(Timestamp time, int timeLimit) throws ParseException
	{
        String judgeTime = String.valueOf(time.getTime()); 
        long seconds = (System.currentTimeMillis() - Long.parseLong(judgeTime)) / (1000 * 60);
        if(seconds > timeLimit)
        {
        	return false;
        }
		return true;
	}
}
