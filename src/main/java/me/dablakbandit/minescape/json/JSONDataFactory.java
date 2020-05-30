/*
 * Copyright (c) 2019 Ashley Thew
 */

package me.dablakbandit.minescape.json;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class JSONDataFactory implements TypeAdapterFactory{
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken){
		TypeAdapter<T> delegate = gson.getDelegateAdapter(this, typeToken);
		return new TypeAdapter<T>(){
			@Override
			public void write(JsonWriter jsonWriter, T t) throws IOException{
				if(t instanceof JSONTerm){
					((JSONTerm)t).term();
				}
				delegate.write(jsonWriter, t);
			}
			
			@Override
			public T read(JsonReader jsonReader) throws IOException{
				T val = delegate.read(jsonReader);
				if(val instanceof JSONInit){
					((JSONInit)val).init();
				}
				return val;
			}
		};
	}
}
