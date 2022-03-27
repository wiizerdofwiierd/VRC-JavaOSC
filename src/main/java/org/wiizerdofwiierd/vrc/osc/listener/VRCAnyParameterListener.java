package org.wiizerdofwiierd.vrc.osc.listener;

import org.wiizerdofwiierd.vrc.osc.bridge.event.VRCAvatarParameterChangeEvent;

/**
 * Interface for receiving {@link VRCAvatarParameterChangeEvent}s
 * <br>
 * The events received by the implementing class will always be of type {@link Object}
 */
public interface VRCAnyParameterListener extends VRCAvatarParameterListener<Object>{

	@Override
	void onParameterChanged(VRCAvatarParameterChangeEvent<Object> event);
}
