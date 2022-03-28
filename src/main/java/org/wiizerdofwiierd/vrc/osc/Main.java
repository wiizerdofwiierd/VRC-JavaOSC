package org.wiizerdofwiierd.vrc.osc;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.wiizerdofwiierd.vrc.osc.bridge.VRCOsc;
import org.wiizerdofwiierd.vrc.osc.demo.Demo;

import java.io.IOException;

public class Main{
	
	private static VRCOsc osc;

	public static void main(String[] args){
		// Initialize logger and set level to Info
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
		
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
