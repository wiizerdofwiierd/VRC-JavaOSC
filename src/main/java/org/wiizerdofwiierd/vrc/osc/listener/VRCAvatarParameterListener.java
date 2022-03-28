package org.wiizerdofwiierd.vrc.osc.listener;

import org.wiizerdofwiierd.vrc.osc.bridge.event.VRCAvatarParameterChangeEvent;

/**
 * Interface for receiving {@link VRCAvatarParameterChangeEvent}s

 * @param <T> Type of the parameter(s) being listened to
 */
public interface VRCAvatarParameterListener<T>{
	
	void onParameterChanged(VRCAvatarParameterChangeEvent<T> event);
}
