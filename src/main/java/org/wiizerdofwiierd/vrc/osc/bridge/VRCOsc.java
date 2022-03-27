package org.wiizerdofwiierd.vrc.osc.bridge;

import com.illposed.osc.MessageSelector;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCMessageListener;
import com.illposed.osc.OSCSerializeException;
import com.illposed.osc.messageselector.OSCPatternAddressMessageSelector;
import com.illposed.osc.transport.udp.OSCPortIn;
import com.illposed.osc.transport.udp.OSCPortOut;
import com.sun.istack.internal.Nullable;
import org.wiizerdofwiierd.vrc.osc.bridge.event.VRCAvatarParameterChangeEvent;
import org.wiizerdofwiierd.vrc.osc.bridge.parameter.VRCAvatarParameter;
import org.wiizerdofwiierd.vrc.osc.bridge.parameter.VRCAvatarParameters;
import org.wiizerdofwiierd.vrc.osc.listener.VRCAvatarParameterListener;
import org.wiizerdofwiierd.vrc.osc.listener.VRCAvatarParameterStore;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for handling OSC connections (inbound & outbound) between VRChat and your application. Represents a two-way connection.
 * To create a connection with the default values used by VRChat, use:
 * <pre>
 *     VRCOscConnection.builder().build();
 * </pre>
 * @see #builder() 
 */
public class VRCOsc{

	/**
	 * The default port on which VRChat listens for incoming OSC messages
	 */
	public static final int VRC_OSC_PORT_IN = 9000;
	/**
	 * The default port on which VRChat sends OSC messages
	 */
	public static final int VRC_OSC_PORT_OUT = 9001;
	
	private static final MessageSelector MESSAGE_SELECTOR = 
			new OSCPatternAddressMessageSelector(VRCAvatarParameters.PARAMETER_PATH + "*");
	
	private String ip = "localhost";
	private int portIn = VRC_OSC_PORT_OUT;
	private int portOut = VRC_OSC_PORT_IN;
	private boolean isCacheEnabled;

	private OSCPortIn listener;
	private OSCPortOut remote;
	
	private HashMap<VRCAvatarParameterListener<?>, OSCMessageListener> oscListeners;
	private VRCAvatarParameterStore parameterStore;
	
	private VRCOsc(){
		oscListeners = new HashMap<>();
	}

	/**
	 * Builder for {@link VRCOsc}. To get an instance, use {@link VRCOsc#builder()}
	 */
	public static final class VRCOscBuilder{
		private final VRCOsc osc;
		
		private VRCOscBuilder(){
			this.osc = new VRCOsc();
		}
		
		public VRCOsc build() throws IOException{
			this.osc.connect();
			this.osc.listen();
			
			return this.osc;
		}
		
		public VRCOscBuilder withIp(String ip){
			this.osc.ip = ip;
			return this;
		}
		
		public VRCOscBuilder receiveOn(int port){
			this.osc.portIn = port;
			return this;
		}
		
		public VRCOscBuilder sendOn(int port){
			this.osc.portOut = port;
			return this;
		}
		
		public VRCOscBuilder withCacheEnabled(boolean enabled){
			this.osc.isCacheEnabled = enabled;
			return this;
		}
	}

	/**
	 * Returns a {@link VRCOscBuilder VRCOscBuilder} used to create a {@link VRCOsc}.<br>
	 * The builder is populated with the default values used by VRChat. If you have not changed these values 
	 * by launching the game with the <code>--osc</code> parameter, you can get a working connection using:
	 * <pre>
	 *     VRCOsc.builder().build();
	 * </pre>
	 * {@link VRCOsc} provides two methods allowing you to retrieve the last known value of an avatar parameter. These are:
	 * <ul>
	 *     <li>{@link #getParameterValue(String)}</li>
	 *     <li>{@link #getParameterValue(String, Object)}</li>
	 * </ul>
	 * To enable use of these methods, add <code>.withCacheEnabled(true)</code> to your builder
	 */
	public static VRCOscBuilder builder(){
		return new VRCOscBuilder();
	}

	/**
	 * Registers a listener that listens for changes to avatar parameters. 
	 * If a <code>target</code> is provided, the listener will only receive events for the parameter matching the given target.
	 * If <code>target</code> is <code>null</code>, the listener will receive events for any parameter that changes
	 * 
	 * @param listener The {@link VRCAvatarParameterListener} to receive events
	 * @param target Name of the avatar parameter to listen for. Use <code>null</code> for any avatar parameter
	 * @param <T> The type corresponding to the given avatar parameter
	 */
	public <T> void registerListener(VRCAvatarParameterListener<T> listener,  @Nullable String target){
		OSCMessageListener oscListener = (event) -> {
			try{
				OSCMessage message = event.getMessage();
				String address = message.getAddress();

				List<Object> arguments = message.getArguments();
				if(arguments.size() == 0){
					return;
				}


				Pattern parameterAddressPattern = Pattern.compile("(/avatar/parameters/)(\\w+)");
				Matcher matcher = parameterAddressPattern.matcher(address);

				if(!matcher.matches()){
					return;
				}

				String parameterPath = matcher.group(1);
				String parameterName = matcher.group(2);
				
				Object value = arguments.get(0);
				VRCAvatarParameter<T> parameter = new VRCAvatarParameter<>(parameterPath, parameterName);
				
				VRCAvatarParameterChangeEvent<T> parameterEvent = new VRCAvatarParameterChangeEvent<>(parameter, (T) value);

				if(target != null && !parameterName.equals(target)){
					return;
				}

				listener.onParameterChanged(parameterEvent);
			}
			catch(Exception e){
				e.printStackTrace();;
			}
		};

		this.listener.getDispatcher().addListener(MESSAGE_SELECTOR, oscListener);
		this.oscListeners.put(listener, oscListener);
	}

	/**
	 * Registers a listener that listens for changes to a specific avatar parameter. If <code>target</code>
	 * is <code>null</code>, the listener will receive events when any avatar parameter changes.
	 * <br>
	 * To register a listener using the parameter's name instead, use {@link #registerListener(VRCAvatarParameterListener, String)}
	 *
	 * @param listener The {@link VRCAvatarParameterListener} to receive events
	 * @param target The {@link VRCAvatarParameter} to listen for.
	 * @param <T> The type corresponding to the given avatar parameter
	 * @see #registerListener(VRCAvatarParameterListener, String)
	 */
	public <T> void registerListener(VRCAvatarParameterListener<T> listener, @Nullable VRCAvatarParameter<T> target){
		registerListener(listener, target.getName());
	}

	/**
	 * Registers a listener that listens for changes to all avatar parameters. 
	 * To listen for a specific avatar parameter, use {@link #registerListener(VRCAvatarParameterListener, String)} instead
	 *
	 * @param listener The {@link VRCAvatarParameterListener} to receive events
	 * @see VRCAvatarParameterListener
	 * @see #registerListener(VRCAvatarParameterListener, String)
	 * @see #registerListener(VRCAvatarParameterListener, VRCAvatarParameter)
	 */
	public <T> void registerListener(VRCAvatarParameterListener<T> listener){
		registerListener(listener, (String) null);
	}

	/**
	 * Unregister a listener. The listener will no longer receive events after it is unregistered
	 * 
	 * @param listener The {@link VRCAvatarParameterListener} to unregister
	 * @param <T> The type corresponding to the parameters listened to by the listener
	 * @return <code>true</code> if the listener was removed, <code>false</code> if the listener was not registered
	 */
	public <T> boolean unregisterListener(VRCAvatarParameterListener<T> listener){
		if(!oscListeners.containsKey(listener)){
			return false;
		}
		
		OSCMessageListener oscListener = oscListeners.get(listener);
		this.listener.getDispatcher().removeListener(MESSAGE_SELECTOR, oscListener);
		
		return true;
	}

	/**
	 * Set the value of an avatar parameter
	 * An instance of each of the default parameters is provided in the {@link VRCAvatarParameters} class
	 * To use the parameter's name instead, see {@link #setParameterValue(String, Object)}
	 * 
	 * @param parameter A {@link VRCAvatarParameter} representing an in-game avatar parameter
	 * @param value New value of the avatar parameter
	 * @param <T> The type of the avatar parameter
	 * @return <code>true</code> if the message was successfully sent, <code>false</code> otherwise
	 * @see VRCAvatarParameters
	 * @see #setParameterValue(String, Object) 
	 */
	public <T> boolean setParameterValue(VRCAvatarParameter<T> parameter, T value){
		return setParameterValue(parameter.getName(), value);
	}

	/**
	 * Set the value of an avatar parameter by its name
	 * @param parameterName Name of the avatar parameter
	 * @param value New value of the avatar parameter
	 * @return <code>true</code> if the message was successfully sent, <code>false</code> otherwise
	 */
	public boolean setParameterValue(String parameterName, Object value){
		if(!this.remote.isConnected()){
			throw new IllegalStateException("Outbound OSC connection is not established");
		}
		
		String address = String.format("/avatar/parameters/%s", parameterName);
		
		try{
			this.remote.send(new OSCMessage(address, Collections.singletonList(value)));
			return true;
		}
		catch(IOException | OSCSerializeException e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * <p>
	 *     Get the (last known) value of an avatar parameter. If the value is unknown—due
	 *     to it either not existing, or not yet changing—this will return <code>null</code>
	 * </p>
	 * <br>
	 * <p>An instance of each of the default parameters is provided in the {@link VRCAvatarParameters} class</p>
	 * <br>
	 * <p><i>You must enable caching when building the {@link VRCOsc} instance for this to work</i></p>
	 *
	 * @param parameter The {@link VRCAvatarParameter} to retrieve the value for
	 * @throws UnsupportedOperationException If caching is not enabled on this {@link VRCOsc}
	 * @return The last known value of the specified parameter, or <code>null</code> if the value is unknown
	 * @see VRCAvatarParameters
	 */
	public <T> T getParameterValue(VRCAvatarParameter<T> parameter){
		return (T) getParameterValue(parameter.getName());
	}

	/**
	 * <p>
	 *     Get the (last known) value of an avatar parameter. If the value is unknown—due
	 *     to it either not existing, or not yet changing—<code>defaultValue</code> is returned instead   
	 * </p>
	 * <br>
	 * <p>An instance of each of the default parameters is provided in the {@link VRCAvatarParameters} class</p>
	 * <br>
	 * <p><i>You must enable caching when building the {@link VRCOsc} instance for this to work</i></p>
	 *
	 * @param parameter The {@link VRCAvatarParameter} to retrieve the value for
	 * @param defaultValue Value that is returned instead if the current value is unknown
	 * @param <T> The type of the avatar parameter
	 * @throws ClassCastException If the type of <code>defaultValue</code> is not compatible with the parameter
	 * @return The last known value of the specified parameter, or <code>null</code> if the value is unknown
	 * @see VRCAvatarParameters
	 */
	public <T> T getParameterValue(VRCAvatarParameter<T> parameter, T defaultValue){
		return getParameterValue(parameter.getName(), defaultValue);
	}
	
	/**
	 * Get the (last known) value of an avatar parameter. If the value is unknown—due 
	 * to it either not existing, or not yet changing—this will return <code>null</code>
	 * <br>
	 * <p><i>You must enable caching when building the {@link VRCOsc} instance for this to work</i></p>
	 * 
	 * @param name Name of the parameter to retrieve the value for
	 * @throws UnsupportedOperationException If caching is not enabled on this {@link VRCOsc}
	 * @return The last known value of the specified parameter, or <code>null</code> if the value is unknown
	 */
	public Object getParameterValue(String name){
		return getParameterValue(name, null);
	}

	/**
	 * Get the (last known) value of an avatar parameter. If the value is unknown—due
	 * to it either not existing, or not yet changing—<code>defaultValue</code> is returned instead
	 * <br>
	 * <p><i>You must enable caching when building the {@link VRCOsc} instance for this to work</i></p>
	 * 
	 * @param name Name of the parameter to retrieve the value for
	 * @param defaultValue Value that is returned instead if the current value is unknown
	 * @param <T> The type of the avatar parameter
	 * @throws ClassCastException If the type of <code>defaultValue</code> is not compatible with the parameter
	 * @return The last known value of the specified parameter, or <code>null</code> if the value is unknown
	 */
	public <T> T getParameterValue(String name, T defaultValue){
		if(!this.isCacheEnabled){
			throw new UnsupportedOperationException(
					"Parameter caching is not enabled. You can enable it on the builder using .withCacheEnabled(true)");
		}
		
		Object value = this.parameterStore.get(name);
		if(value == null){
			return defaultValue;
		}
		
		try{
			return (T) value;
		}
		catch(ClassCastException e){
			throw new ClassCastException(String.format(
					"Value of parameter '%s' can not be cast to type %s", name, defaultValue.getClass().getSimpleName()));
		}
	}

	private void connect() throws IOException{
		this.remote = new OSCPortOut(new InetSocketAddress(this.ip, this.portOut));
		remote.connect();
	}

	private void listen() throws IOException{
		this.listener = new OSCPortIn(this.portIn);
		listener.setDaemonListener(false);
		listener.startListening();
		
		if(this.isCacheEnabled){
			this.parameterStore = new VRCAvatarParameterStore();
			registerListener(event -> parameterStore.set(event.getParameter(), event.getNewValue()));
		}
	}
}
