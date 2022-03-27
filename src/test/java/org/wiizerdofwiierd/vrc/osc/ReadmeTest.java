package org.wiizerdofwiierd.vrc.osc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wiizerdofwiierd.vrc.osc.bridge.VRCOsc;
import org.wiizerdofwiierd.vrc.osc.bridge.event.VRCAvatarParameterChangeEvent;
import org.wiizerdofwiierd.vrc.osc.bridge.parameter.VRCAvatarParameter;
import org.wiizerdofwiierd.vrc.osc.bridge.parameter.VRCAvatarParameters;
import org.wiizerdofwiierd.vrc.osc.listener.VRCAvatarParameterListener;

import java.io.IOException;

/**
 * Test class ensuring that all example snippets from README.md continue to be valid
 */
public class ReadmeTest{
	
	private VRCOsc osc = null;
	
	@BeforeEach
	public void setup() throws Exception{
		osc = VRCOsc.builder().withCacheEnabled(true).build();
	}
	
	@Test
	public void usage_createInstance_compiles(){
		try{
			osc = VRCOsc.builder().build();
		}
		catch(IOException e){
			// Handle exception
		}
	}
	
	@Test
	public void usage_configureSettings_compiles(){
		VRCOsc.builder()
				.withIp("localhost") // Host where OSC messages will be sent to
				.sendOn(9000) // Port to send OSC messages on (Application -> VRChat)
				.receiveOn(9001) // Port to receive OSC messages on (VRChat -> Application)
				.withCacheEnabled(true); // Optional setting that enables you to retrieve the last known value of any parameter
	}
	
	@Test
	public void usage_setParameter_compiles(){
		osc.setParameterValue("MyIntParameter", 0);
	}
	
	@Test
	public void usage_listenAny_compiles(){
		osc.registerListener(event -> System.out.println(event.getNewValue()));
	}
	
	@Test
	public void usage_listenSingle_compiles(){
		osc.registerListener((VRCAvatarParameterListener<Integer>) event -> {
			System.out.println("MyIntParameter changed to: " + event.getNewValue());
		}, "MyIntParameter");
	}
	
	@Test
	public void usage_listenerClass_compiles(){
		class MyIntParameterListener implements VRCAvatarParameterListener<Integer>{

			@Override
			public void onParameterChanged(VRCAvatarParameterChangeEvent<Integer> event){
				System.out.println("MyIntParameter changed to: " + event.getNewValue());
			}
		}

		osc.registerListener(new MyIntParameterListener(), "MyIntParameter");
	}
	
	@Test
	public void usage_getParameter_compiles(){
		osc.getParameterValue("MyIntParameter", 0);
	}
	
	@Test
	public void usage_getTyped_compiles(){
		Boolean isPlayerAfk = osc.getParameterValue(VRCAvatarParameters.AFK);
		Integer playerTrackingType = osc.getParameterValue(VRCAvatarParameters.TRACKING_TYPE);
		Float gestureLeftWeight = osc.getParameterValue(VRCAvatarParameters.GESTURE_LEFT_WEIGHT);
	}
	
	@Test
	public void usage_createAndGetTyped_compiles(){
		final VRCAvatarParameter<Integer> MY_INT_PARAMETER = VRCAvatarParameters.create("MyIntParameter");
		
		Integer myIntParameter = osc.getParameterValue(MY_INT_PARAMETER);
		osc.setParameterValue(MY_INT_PARAMETER, 1); // Also supports setting the value
	}
}
