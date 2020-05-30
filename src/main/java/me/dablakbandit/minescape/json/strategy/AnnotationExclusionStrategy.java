/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.json.strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import me.dablakbandit.minescape.json.Exclude;

public class AnnotationExclusionStrategy implements ExclusionStrategy{
	
	@Override
	public boolean shouldSkipField(FieldAttributes f){
		return f.getAnnotation(Exclude.class) != null;
	}
	
	@Override
	public boolean shouldSkipClass(Class<?> clazz){
		return false;
	}
}
