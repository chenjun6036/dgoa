package com.njust.dg.oa.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonTools {

	public static String ObjectToJson(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	public static <T> T JsonToObject(String jsonString) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(jsonString, new TypeToken<T>() {
			}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return t;
	}
	/*
	 * public static <T> List<T> JsonToList(String jsonString){ List<T> t =null;
	 * try { Gson gson = new Gson();
	 * 
	 * t = gson.fromJson(jsonString, new TypeToken<List<T>>(){}.getType()); }
	 * catch (Exception e) { // TODO: handle exception } return t; }
	 */
}
