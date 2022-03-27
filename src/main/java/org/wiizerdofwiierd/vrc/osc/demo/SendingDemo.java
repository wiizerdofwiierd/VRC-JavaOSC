package org.wiizerdofwiierd.vrc.osc.demo;

public final class SendingDemo implements OscDemo{

	private Demo context;
	
	@Override
	public void start(Demo context){
		this.context = context;

		StringBuilder builder = new StringBuilder("Send OSC messages to VRChat\n")
				.append("Syntax: <parameter> <value>\n")
				.append("Examples:\n")
				.append("MyIntParameter 0\n")
				.append("MyFloatParameter 0.5\n")
				.append("MyBoolParameter true\n")
				.append("Type 'exit' to return to the previous menu\n");
		System.out.print(builder.toString());
		
		while(processNextCommand());
	}
	
	@Override
	public boolean processNextCommand(){
		String input = context.scanner.nextLine();
		if(input.equalsIgnoreCase("exit")) return false;

		// Ensure that the command has at least two arguments, a parameter name and a value
		String[] split = input.split(" ");
		if(split.length < 2){
			System.err.println("Both a parameter name and a value are required");
			return true;
		}
		
		String parameterName = split[0];
		String value = split[1];

		context.osc.setParameterValue(parameterName, convertType(value));
		return true;
	}

	private static Object convertType(String originalValue){
		switch(originalValue.toLowerCase()){
			case "true": return true;
			case "false": return false;
			default:
				try{
					float floatValue = Float.parseFloat(originalValue);
					if(((int) floatValue) == floatValue){
						return (int) floatValue;
					}
					
					return floatValue;
				}
				catch(NumberFormatException e){
					return originalValue;
				}
		}
	}
}
