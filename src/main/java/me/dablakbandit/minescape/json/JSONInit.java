/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.json;

import com.google.gson.JsonObject;

public interface JSONInit{
	
	public static <T> T fromJSON(JsonObject jo, Class<T> clazz){
		return JSON.fromJSON(jo, clazz);
	}
	
	void init();
}
