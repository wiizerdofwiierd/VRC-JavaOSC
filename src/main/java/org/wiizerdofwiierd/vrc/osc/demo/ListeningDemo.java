package org.wiizerdofwiierd.vrc.osc.demo;

import org.wiizerdofwiierd.vrc.osc.listener.VRCAnyParameterListener;

import java.util.HashMap;

public final class ListeningDemo implements OscDemo{

	private Demo context;
	private HashMap<String, VRCAnyParameterListener> listeners;
	
	@Override
	public void start(Demo context){
		this.context = context;
		this.listeners = new HashMap<>();

		StringBuilder builder = new StringBuilder("Receive OSC messages from VRChat\n")
				.append("Syntax: listen/unlisten <parameter/all>\n")
				.append("Examples:\n")
				.append("listen VelocityX\n")
				.append("unlisten VelocityX\n")
				.append("listen all\n")
				.append("unlisten all\n")
				.append("Type 'exit' to return to the previous menu\n");
		System.out.print(builder.toString());
		
		while(processNextCommand());
	}
	
	@Override
	public boolean processNextCommand(){
		String input = context.scanner.nextLine();
		if(input.equalsIgnoreCase("exit")) return false;

		// Ensure that the command has at least two arguments, a command and a parameter name
		String[] split = input.split(" ");
		if(split.length < 2){
			System.err.println("Both an action and a parameter name are required");
			return true;
		}
		
		String action = split[0];
		String parameterName = split[1];
		
		if(action.equalsIgnoreCase("listen")){
			System.out.printf(listenParameter(parameterName), parameterName);
		}
		else if(action.equalsIgnoreCase("unlisten")){
			System.out.printf(unlistenParameter(parameterName), parameterName);
		}
		else{
			System.out.println("Invalid command");
		}
		
		return true;
	}
	
	private String listenParameter(String name){
		String target = name.toLowerCase();
		
		// If we are already listening to this parameter, do nothing else
		if(this.listeners.containsKey(target)){
			return "You are already listening to parameter '%s'!\n";
		}
		
		String message;
		
		// If the special 'all' value is given, stop listening to all parameters and set the target to null
		// This will make the listener receive events for all parameters when it is registered
		if(target.equals("all")){
			unlistenAll();

			target = null;
			message = "Now listening to all parameters\n";
		}
		else{
			message = "Now listening to parameter '%s'\n";
		}
		
		// Create a simple listener that prints a message when the value changes
		// Note that we could use either VRCAnyParameterListener OR VRCSingleParameterListener<Object>
		VRCAnyParameterListener listener = (event) -> {
			System.out.printf("Message received: %s = %s\n", event.getParameter().getFullPath(), event.getNewValue());
		};

		// Register the listener to our OSC handler as well as our map
		context.osc.registerListener(listener, target);
		this.listeners.put(target, listener);

		return message;
	}
	
	private String unlistenParameter(String name){
		// If the special 'all' value is given, stop listening to all parameters. Do nothing else
		if(name.equalsIgnoreCase("all")){
			unlistenAll();
			return "Stopped listening to all parameters\n";
		}

		// If we have not registered a listener for this parameter, do nothing else
		if(!this.listeners.containsKey(name.toLowerCase())){
			return "You are not listening to parameter '%s'!\n";
		}
		
		// Retrieve the listener from our map, unregister it from our OSC handler, and remove it from the map
		VRCAnyParameterListener listener = this.listeners.get(name.toLowerCase());
		context.osc.unregisterListener(listener);
		this.listeners.remove(name.toLowerCase());

		return "Stopped listening to parameter '%s'\n";
	}
	
	private void unlistenAll(){
		for(String key : this.listeners.keySet()){
			VRCAnyParameterListener listener = this.listeners.get(key);
			context.osc.unregisterListener(listener);
		}
		
		this.listeners.clear();
	}
}
