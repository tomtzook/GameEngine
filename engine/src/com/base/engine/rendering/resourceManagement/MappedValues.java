package com.base.engine.rendering.resourceManagement;

import java.util.HashMap;

import com.base.engine.math.Vector3f;

public abstract class MappedValues {

	private HashMap<String, Vector3f> Vector3fHashMap;
	private HashMap<String, Float> floatHashMap;

	public MappedValues()
	{
		Vector3fHashMap = new HashMap<String, Vector3f>();
		floatHashMap = new HashMap<String, Float>();
	}

	public void addVector3f(String name, Vector3f Vector3ff) { 
		Vector3fHashMap.put(name, Vector3ff); 
	}
	public void addFloat(String name, float floatValue) { 
		floatHashMap.put(name, floatValue); 
	}
	public Vector3f getVector3f(String name){
		Vector3f result = Vector3fHashMap.get(name);
		if(result != null)
			return result;

		return new Vector3f();
	}
	public float getFloat(String name){
		Float result = floatHashMap.get(name);
		if(result != null)
			return result;

		return 0;
	}
}
