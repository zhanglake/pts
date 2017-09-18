package com.ptsoft.pts.util;

import java.util.HashMap;
import java.util.Map;

public class GenerateCode 
{
	private static Map<Integer, Character> map = new HashMap<Integer, Character>();
	private static String mapStr = "0123456789ABCDEFGHJKMNPQRSTVWXY";
	
	public static String getSerialNo(String serialNo)
	{
		map = getMap();
		serialNo = getNextChar(serialNo, map);
		return serialNo;
	}
	
	private static Map<Integer, Character> getMap()
	{
		Map<Integer, Character> map = new HashMap<Integer, Character>();
		for (int j = 0; j < mapStr.length(); j++) 
        {
            map.put(j, mapStr.charAt(j));
        }
		return map;
	}

	/**
     * 查找输入字符串的下一个字符串
     *
     * @param str 输入的字符串
     * @param map MAP集合
     * @return 下一个字符串
     */
    private static String getNextChar(String str, Map<Integer, Character> map)
    {
    	int len = str.length() - 1;
        char newStr[] = str.toCharArray();
        int key = 0;
        int i = len;
        //逆序查找第几个为止不为Y
        for (i = len; i >= 0; i--)
        {
            if (newStr[i] != 'Y') 
            {
                break;
            }
        }
        //将为Y的设为0
        for (int k = 0; k <= len - i - 1; k++) 
        {
            newStr[len - k] = '0';
        }
        //不为Y的->对应key+1所在的值
        key = findByKey(newStr[i], map);
        newStr[i] = map.get(key + 1);
        return String.valueOf(newStr);
    }
    
    /**
     * 查找字符对应MAP集合中的key值
     *
     * @param newStr 输入字符
     * @param map    MAP集合
     * @return key值
     */
    private static int findByKey(Character newStr, Map<Integer, Character> map) 
    {
        int key = 0;
        for (Map.Entry<Integer, Character> entry : map.entrySet()) 
        {
            if (newStr == entry.getValue()) 
            {
                key = entry.getKey();
                break;
            }
        }
        return key;
    }

    // 从字母开始编码
	private static String getNextChar(String str)
	{
		char charStr[] = str.toCharArray();
		for(int i = charStr.length - 1; i >= 0; i--)
		{
		    int chr = charStr[i];
		    if(chr >= 65 && chr < 90) //A-Z
		    {
		    	chr = chr + 1;
		    	String cur = (char)chr + "";		    	  
		    	//跳过 IOLZU 五个字母
		    	if(!cur.equals("Z"))
		    	{
		    		if(cur.equals("I") || cur.equals("O") || cur.equals("L") || cur.equals("U"))
		    		{
		    			chr = chr + 1;
		    		}
		    		charStr[i] = (char)chr;
		    		break;
		    	}		    	 
		    }
		    else if(chr >= 48 && chr <= 57)
		    {
		    	if(chr == 57)  //当前为9从A开始
		    	{
		    		chr = 65;
		    	}
		    	else
		    	{
		    		chr = chr + 1;
		    	}
		    	charStr[i] = (char)chr;
		    	break;
		    }
		}
		return String.valueOf(charStr);		   
	}
	
	public static void main(String[] args)
	{
		String str = "00000";
		int sum = 0;
		while (!str.equals("YYYYY")) 
		{
			sum++;
			str = getNextChar(str);
		}
		System.out.println(sum);
		System.out.println("---------------");
		
		
		//0000-0000Y-00010-0001Y-00020-0002Y...YYYYY
        String mapStr = "0123456789ABCDEFGHJKMNPQRSTVWXY";
        String result = "00000";
        Map<Integer, Character> map = new HashMap<Integer, Character>();
        for (int j = 0; j < mapStr.length(); j++) 
        {
            map.put(j, mapStr.charAt(j));
        }
        sum = 0;
        while(!result.equals("YYYYY")) 
        {
        	result = getNextChar(result, map);
            sum++;
        }
        System.out.println(result);
        System.out.println(sum);
	}
}
