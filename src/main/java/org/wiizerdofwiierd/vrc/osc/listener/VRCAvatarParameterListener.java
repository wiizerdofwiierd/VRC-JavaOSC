package org.wiizerdofwiierd.vrc.osc.listener;

import org.wiizerdofwiierd.vrc.osc.bridge.event.VRCAvatarParameterChangeEvent;

/**
 * Interface for receiving {@link VRCAvatarParameterChangeEvent}s
 * <br>
 * This is a parent interface used to provide varying levels of abstraction to its sub-interfaces. For creating your own listener,
 * please implement {@link VRCAnyParameterListener} or {@link VRCSingleParameterListener}
 * 
 * @param <T> Type of event that this listener handles
 * @see VRCAnyParameterListener
 * @see VRCSingleParameterListener
 */
public interface VRCAvatarParameterListener<T extends VRCAvatarParameterChangeEvent<?>>{
	
	void onParameterChanged(T event);
}
