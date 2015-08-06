package com.mengzhu.daily.util;

import com.google.gson.Gson;

public class GsonUtil {
	
	public static <T> String objToStr(T obj){
		Gson gson = new Gson();
		String str = gson.toJson(obj);
		System.out.println("str " + str);
		return str;
	}
	
	public static <T> T strToObj(Class<T> tClass, String str){
		Gson gson = new Gson();
		T t =  gson.fromJson(str, tClass);
		System.out.println("t " + t.toString());
		return t;
	}

}
