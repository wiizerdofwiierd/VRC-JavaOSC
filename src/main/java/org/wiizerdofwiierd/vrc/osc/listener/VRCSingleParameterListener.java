package org.wiizerdofwiierd.vrc.osc.listener;

import org.wiizerdofwiierd.vrc.osc.bridge.event.VRCAvatarParameterChangeEvent;

/**
 * Interface for receiving {@link VRCAvatarParameterChangeEvent}s
 * <br>
 * The events received by the implementing class will always be of type {@link Object}
 * @param <T> Type of the parameter(s) being listened to
 */
public interface VRCSingleParameterListener<T> extends VRCAvatarParameterListener<VRCAvatarParameterChangeEvent<T>>{

	@Override
	void onParameterChanged(VRCAvatarParameterChangeEvent<T> event);
}
