package org.wiizerdofwiierd.vrc.osc.bridge.parameter;

/**
 * This class holds an instance of a {@link VRCAvatarParameter} representing each of the default parameters:
 * <table>
 *     <tr>
 *         <th>Symbol</th>
 *         <th>Parameter Name</th>
 *         <th>Type</th>
 *     </tr>
 *     <tr>
 *         <td>{@link #EMOTE}</td>
 *         <td><code>"VRCEmote"</code></td>
 *         <td>{@link Integer}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #FACE_BLEND_H}</td>
 *         <td><code>"VRCFaceBlendH"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #FACE_BLEND_V}</td>
 *         <td><code>"VRCFaceBlendV"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>     
 *     <tr>
 *         <td>{@link #IS_LOCAL}</td>
 *         <td><code>"IsLocal"</code></td>
 *         <td>{@link Boolean}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #VISEME} </td>
 *         <td><code>"Viseme"</code></td>
 *         <td>{@link Integer}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #VOICE}</td>
 *         <td><code>"Voice"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #GESTURE_LEFT}</td>
 *         <td><code>"GestureLeft"</code></td>
 *         <td>{@link Integer}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #GESTURE_RIGHT}</td>
 *         <td><code>"GestureRight"</code></td>
 *         <td>{@link Integer}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #GESTURE_LEFT_WEIGHT}</td>
 *         <td><code>"GestureLeftWeight"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #GESTURE_RIGHT_WEIGHT}</td>
 *         <td><code>"GestureRightWeight"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #ANGULAR_Y}</td>
 *         <td><code>"AngularY"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #VELOCITY_X}</td>
 *         <td><code>"VelocityX"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #VELOCITY_Y}</td>
 *         <td><code>"VelocityY"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #VELOCITY_Z}</td>
 *         <td><code>"VelocityZ"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #UPRIGHT}</td>
 *         <td><code>"Upright"</code></td>
 *         <td>{@link Float}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #GROUNDED}</td>
 *         <td><code>"Grounded"</code></td>
 *         <td>{@link Boolean}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #SEATED}</td>
 *         <td><code>"Seated"</code></td>
 *         <td>{@link Boolean}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #AFK}</td>
 *         <td><code>"AFK"</code></td>
 *         <td>{@link Boolean}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #TRACKING_TYPE}</td>
 *         <td><code>"TrackingType"</code></td>
 *         <td>{@link Integer}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #VR_MODE}</td>
 *         <td><code>"VRMode"</code></td>
 *         <td>{@link Integer}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #MUTE_SELF}</td>
 *         <td><code>"MuteSelf"</code></td>
 *         <td>{@link Boolean}</td>
 *     </tr>
 *     <tr>
 *         <td>{@link #IN_STATION}</td>
 *         <td><code>"InStation"</code></td>
 *         <td>{@link Boolean}</td>
 *     </tr>
 * </table>
 */
public final class VRCAvatarParameters{
	public static final String PARAMETER_PATH = "/avatar/parameters/";
	
	public static final VRCAvatarParameter<Integer> EMOTE				= new VRCAvatarParameter<>(PARAMETER_PATH, "VRCEmote");
	public static final VRCAvatarParameter<Float>	FACE_BLEND_H		= new VRCAvatarParameter<>(PARAMETER_PATH, "VRCFaceBlendH");
	public static final VRCAvatarParameter<Float>	FACE_BLEND_V		= new VRCAvatarParameter<>(PARAMETER_PATH, "VRCFaceBlendV");
	public static final VRCAvatarParameter<Boolean> IS_LOCAL 			= new VRCAvatarParameter<>(PARAMETER_PATH, "IsLocal");
	public static final VRCAvatarParameter<Integer> VISEME 				= new VRCAvatarParameter<>(PARAMETER_PATH, "Viseme");
	public static final VRCAvatarParameter<Float> 	VOICE 				= new VRCAvatarParameter<>(PARAMETER_PATH, "Voice");
	public static final VRCAvatarParameter<Integer> GESTURE_LEFT 		= new VRCAvatarParameter<>(PARAMETER_PATH, "GestureLeft");
	public static final VRCAvatarParameter<Integer> GESTURE_RIGHT 		= new VRCAvatarParameter<>(PARAMETER_PATH, "GestureRight");
	public static final VRCAvatarParameter<Float> 	GESTURE_LEFT_WEIGHT = new VRCAvatarParameter<>(PARAMETER_PATH, "GestureLeftWeight");
	public static final VRCAvatarParameter<Float> 	GESTURE_RIGHT_WEIGHT= new VRCAvatarParameter<>(PARAMETER_PATH, "GestureRightWeight");
	public static final VRCAvatarParameter<Float> 	ANGULAR_Y 			= new VRCAvatarParameter<>(PARAMETER_PATH, "AngularY");
	public static final VRCAvatarParameter<Float> 	VELOCITY_X 			= new VRCAvatarParameter<>(PARAMETER_PATH, "VelocityX");
	public static final VRCAvatarParameter<Float> 	VELOCITY_Y 			= new VRCAvatarParameter<>(PARAMETER_PATH, "VelocityY");
	public static final VRCAvatarParameter<Float> 	VELOCITY_Z 			= new VRCAvatarParameter<>(PARAMETER_PATH, "VelocityZ");
	public static final VRCAvatarParameter<Float> 	UPRIGHT 			= new VRCAvatarParameter<>(PARAMETER_PATH, "Upright");
	public static final VRCAvatarParameter<Boolean> GROUNDED 			= new VRCAvatarParameter<>(PARAMETER_PATH, "Grounded");
	public static final VRCAvatarParameter<Boolean> SEATED 				= new VRCAvatarParameter<>(PARAMETER_PATH, "Seated");
	public static final VRCAvatarParameter<Boolean> AFK 				= new VRCAvatarParameter<>(PARAMETER_PATH, "AFK");
	public static final VRCAvatarParameter<Integer> TRACKING_TYPE 		= new VRCAvatarParameter<>(PARAMETER_PATH, "TrackingType");
	public static final VRCAvatarParameter<Integer> VR_MODE 			= new VRCAvatarParameter<>(PARAMETER_PATH, "VRMode");
	public static final VRCAvatarParameter<Boolean> MUTE_SELF 			= new VRCAvatarParameter<>(PARAMETER_PATH, "MuteSelf");
	public static final VRCAvatarParameter<Boolean> IN_STATION 			= new VRCAvatarParameter<>(PARAMETER_PATH, "InStation");
	
	public static <T> VRCAvatarParameter<T> create(String name){
		return new VRCAvatarParameter<T>(PARAMETER_PATH, name);
	}
}
