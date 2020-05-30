/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.json;

import com.google.gson.JsonObject;

public class JSONData implements JSONInit, JSONTerm{
	
	public static <T> T fromJSON(JsonObject jo, Class<T> clazz){
		return JSONInit.fromJSON(jo, clazz);
	}
	
	public JsonObject toJSON(){
		return JSON.toJSON(this);
	}
	
	@Override
	
	public void init(){
		
	}
	
	@Override
	public void term(){
		
	}
}
