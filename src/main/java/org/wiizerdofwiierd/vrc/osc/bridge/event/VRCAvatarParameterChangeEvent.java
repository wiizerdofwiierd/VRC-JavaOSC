package org.wiizerdofwiierd.vrc.osc.bridge.event;

import org.wiizerdofwiierd.vrc.osc.bridge.parameter.VRCAvatarParameter;

/**
 * Represents a single occurrence of an avatar parameter changing values
 * @param <T> Type of the parameter
 */
public class VRCAvatarParameterChangeEvent<T>{
	
	private final VRCAvatarParameter<T> parameter;
	private final T newValue;
	
	public VRCAvatarParameterChangeEvent(VRCAvatarParameter<T> parameter, T newValue){
		this.parameter = parameter;
		this.newValue = newValue;
	}
	
	public VRCAvatarParameter<?> getParameter(){
		return this.parameter;
	}
	
	public T getNewValue(){
		return this.newValue;
	}

	public String getValueAsString(){
		if(this.newValue instanceof String){
			return (String) this.newValue;
		}

		throw new ClassCastException(String.format(
				"Value of parameter %s can not be cast to type String", this.parameter.getName()));
	}

	public boolean getValueAsBoolean(){
		if(this.newValue instanceof Boolean){
			return (Boolean) this.newValue;
		}

		throw new ClassCastException(String.format(
				"Value of parameter %s can not be cast to type Boolean", this.parameter.getName()));
	}

	public int getValueAsInt(){
		if(this.newValue instanceof Integer){
			return (Integer) this.newValue;
		}

		throw new ClassCastException(String.format(
				"Value of parameter %s can not be cast to type Integer", this.parameter.getName()));
	}

	public float getValueAsFloat(){
		if(this.newValue instanceof Float){
			return (Float) this.newValue;
		}

		throw new ClassCastException(String.format(
				"Value of parameter %s can not be cast to type Float", this.parameter.getName()));
	}
	
	@Override
	public String toString(){
		return String.format("%s(parameter=%s, newValue=%s", 
				this.getClass().getSimpleName(), 
				this.parameter, 
				this.newValue);
	}
}
