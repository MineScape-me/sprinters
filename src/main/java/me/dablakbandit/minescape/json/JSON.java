/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.json;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

import org.bukkit.Location;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.dablakbandit.core.utils.NMSUtils;
import me.dablakbandit.core.utils.jsonformatter.JSONFormatter;
import me.dablakbandit.minescape.json.strategy.AnnotationExclusionStrategy;
import me.dablakbandit.minescape.json.strategy.CorePlayersExclusionStrategy;
import me.dablakbandit.minescape.json.types.JSONFormatterSerializer;
import me.dablakbandit.minescape.json.types.LocationSerializer;

public class JSON{
	
	private static Gson			gson;
	private static JsonParser	parser	= new JsonParser();
	
	static{
		GsonBuilder builder = new GsonBuilder();
		builder.serializeNulls();
		builder.registerTypeAdapterFactory(new JSONDataFactory());
		builder.setExclusionStrategies(new AnnotationExclusionStrategy());
		builder.setExclusionStrategies(new CorePlayersExclusionStrategy());
		builder.registerTypeAdapter(JSONFormatter.class, new JSONFormatterSerializer());
		builder.registerTypeAdapter(Location.class, new LocationSerializer());
		gson = builder.create();
	}
	
	public static <T> T fromJSON(JsonObject jo, Class<T> clazz){
		return gson.fromJson(jo, clazz);
	}
	
	public static <T> T fromJSON(String json, Class<T> clazz){
		return gson.fromJson(json, clazz);
	}
	
	public static JsonObject toJSON(Object o){
		return parser.parse(gson.toJson(o)).getAsJsonObject();
	}
	
	public static <T> T loadAndCopy(T object, String json){
		Object cloned = fromJSON(json, object.getClass());
		try{
			NMSUtils.getFields(object.getClass()).forEach(field -> {
				if(field.getAnnotation(Exclude.class) != null){ return; }
				if(Modifier.isStatic(field.getModifiers())){ return; }
				if(field.getDeclaringClass().equals(object.getClass())){
					try{
						Object original = field.get(object);
						Object value = field.get(cloned);
						if(original instanceof Collection){
							if(value == null){ return; }
							((Collection)original).addAll((Collection)value);
						}else if(original instanceof Map){
							if(value == null){ return; }
							((Map)original).putAll((Map)value);
						}
						field.set(object, value);
					}catch(Exception e){
						System.err.println(field.getName());
						e.printStackTrace();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		return object;
	}
}
