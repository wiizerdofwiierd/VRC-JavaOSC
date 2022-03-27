package org.wiizerdofwiierd.vrc.osc.demo;

public enum DemoSelection{
	
	SENDING(1, "Send messages to VRChat", new SendingDemo()),
	LISTENING(2, "Listen to messages from VRChat", new ListeningDemo());

	private int index;
	private String description;
	private OscDemo demo;
	
	DemoSelection(int index, String description, OscDemo demo){
		this.index = index;
		this.description = description;
		this.demo = demo;
	}
	
	public static DemoSelection getDemoOption(int index){
		for(DemoSelection o : values()){
			if(o.index == index){
				return o;
			}
		}
		
		return null;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public OscDemo getDemo(){
		return this.demo;
	}
}
