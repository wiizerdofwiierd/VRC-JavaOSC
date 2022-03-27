package org.wiizerdofwiierd.vrc.osc.demo;

import org.wiizerdofwiierd.vrc.osc.bridge.VRCOsc;

import java.util.Scanner;

public final class Demo extends Thread{
	
	VRCOsc osc;
	Scanner scanner;
	
	public Demo(VRCOsc osc){
		this.osc = osc;
		this.scanner = new Scanner(System.in);
	}
	
	@Override
	public void run(){
		while(true){
			clearOutput();
			
			OscDemo demo = selectDemo();
			if(demo != null){
				clearOutput();
				demo.start(this);
			}
		}
	}

	/**
	 * "Clears" the console output by printing a bunch of newlines
	 */
	public static void clearOutput(){
		for(int i = 0;i < 50;i++){
			System.out.println("\r\n");
		}
	}
	
	/**
	 * Clears the console window and renders the demo selection menu
	 */
	private void renderMenu(){
		StringBuilder builder = new StringBuilder();
		
		for(DemoSelection s : DemoSelection.values()){
			builder.append(s.getIndex())
					.append(". ")
					.append(s.getDescription())
					.append("\n");
		}
		
		builder.append("Select an option: ");
		System.out.print(builder.toString());
	}

	/**
	 * Renders the demo selection menu and prompts the user to select an option
	 */
	private OscDemo selectDemo(){
		renderMenu();
		
		String input = scanner.nextLine();
		int index;
		try{
			index = Integer.parseInt(input);
		}
		catch(NumberFormatException e){
			return null;
		}
		
		DemoSelection selection = DemoSelection.getDemoOption(index);
		if(selection == null){
			return null;
		}
		
		return selection.getDemo();
	}
}
