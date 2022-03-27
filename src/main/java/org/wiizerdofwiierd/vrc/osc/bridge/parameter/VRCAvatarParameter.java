package org.wiizerdofwiierd.vrc.osc.bridge.parameter;

/**
 * Represents a single avatar parameter within VRChat. Represents its presence only, does not store values
 * @param <T> Type of the parameter
 */
public class VRCAvatarParameter<T>{
	
	private final String path;
	private final String name;
	
	public VRCAvatarParameter(String path, String name){
		this.path = path;
		this.name = name;
	}
	
	public String getPath(){
		return this.path;
	}
	
	public String getName(){
		return this.name;
	}

	/**
	 * Gets the full OSC message path of this parameter (e.g. /avatar/parameters/AFK)
	 * @return The full OSC message path of this parameter
	 */
	public String getFullPath(){
		return this.path + this.name;
	}

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		VRCAvatarParameter<?> that = (VRCAvatarParameter<?>) o;

		if(!path.equals(that.path)) return false;
		return name.equals(that.name);
	}

	@Override
	public int hashCode(){
		int result = path.hashCode();
		result = 31 * result + name.hashCode();
		return result;
	}
	
	@Override
	public String toString(){
		return this.getFullPath();
	}
}
