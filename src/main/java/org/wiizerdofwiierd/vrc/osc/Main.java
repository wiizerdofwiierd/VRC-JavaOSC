package org.wiizerdofwiierd.vrc.osc;

import org.wiizerdofwiierd.vrc.osc.bridge.VRCOsc;
import org.wiizerdofwiierd.vrc.osc.bridge.parameter.VRCAvatarParameter;
import org.wiizerdofwiierd.vrc.osc.bridge.parameter.VRCAvatarParameters;
import org.wiizerdofwiierd.vrc.osc.demo.Demo;

import java.io.IOException;

public class Main{

	public static final VRCAvatarParameter<Integer> MY_INT_PARAMETER = VRCAvatarParameters.create("MyIntParameter");
	private static VRCOsc osc;

	public static void main(String[] args){
		try{
			osc = VRCOsc.builder().withCacheEnabled(true).build();
		}
		catch(IOException e){
			System.err.println("Failed to connect to remote OSC server:");

			e.printStackTrace();
			System.exit(-1);
		}
		
		Demo demo = new Demo(osc);
		demo.start();
	}
}
