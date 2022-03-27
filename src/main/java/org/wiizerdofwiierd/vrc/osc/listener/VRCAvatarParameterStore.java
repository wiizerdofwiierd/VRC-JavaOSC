package org.wiizerdofwiierd.vrc.osc.listener;

import org.wiizerdofwiierd.vrc.osc.bridge.parameter.VRCAvatarParameter;

import java.util.HashMap;

public class VRCAvatarParameterStore{
	private final HashMap<String, Object> storage;
	
	public VRCAvatarParameterStore(){
		this.storage = new HashMap<>();
	}
	
	public Object get(String name){
		return this.storage.get(name);
	}
	
	public Object get(VRCAvatarParameter<?> parameter){
		return get(parameter.getName());
	}

	public void set(VRCAvatarParameter<?> parameter, Object value){
		this.storage.put(parameter.getName(), value);
	}
}
