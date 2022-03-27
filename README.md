# VRC-JavaOSC

This library allows you to send and receive [OSC messages](https://docs.vrchat.com/docs/osc-overview) to and from VRChat.

## Features
With this library, you can:
- Set the value of an avatar parameter
- Listen for changes to one or all avatar parameter(s)
- Get the last known value of an avatar parameter

## Adding to your project
### Maven
TODO

## Usage
### Creating a VRCOsc instance
`VRCOsc` is the class handling all the interactions of this library. To get an instance:
```Java
VRCOsc osc = null;
try{
    osc = VRCOsc.builder().build();
}
catch(IOException e){
    // Handle exception
}
```
Without specifying any values, the instance is configured to use VRChat's default settings for OSC communication:
- Messages will be *sent* to **localhost:9000**
- Messages will be *received* on all local network interfaces on port **9001**

### Configuring settings
The following methods are available on the builder:
```Java
VRCOsc.builder()
    .withIp("localhost") // Host where OSC messages will be sent to
    .sendOn(9000) // Port to send OSC messages on (Application -> VRChat)
    .receiveOn(9001) // Port to receive OSC messages on (VRChat -> Application)
    .withCacheEnabled(true); // Optional setting that enables you to retrieve the last known value of any parameter
```

### Changing avatar parameters
Once you have your instance, you can use the following method to set the values of your avatar's parameters in-game:
```Java
osc.setParameterValue(<name>, <value>)
```
For example: `osc.setParameterValue("MyIntParameter", 0)`

### Listening for changes in avatar parameters
For communicating the other way, you'll need to register a *listener*. The following is an example of registering a 
simple anonymous listener which will print a message to `System.out` when **any parameter** changes:
```Java
osc.registerParameterListener(event -> System.out.println(event.getNewValue());
```

To listen for **a specific parameter**, use the following syntax:
```Java
osc.registerListener((VRCSingleParameterListener<Integer>) event -> {
    System.out.println("MyIntParameter changed to: " + event.getNewValue());
}, "MyIntParameter");
```
Of course, you can also create a separate class for your listener. Just make sure it implements the 
`VRCSingleParameterListener<T>` interface:
```Java
public class MyIntParameterListener implements VRCSingleParameterListener<Integer>{

    @Override
    public void onParameterChanged(VRCAvatarParameterChangeEvent<Integer> event){
        System.out.println("MyIntParameter changed to: " + event.getNewValue());
    }
}
```
Then register the listener:
```Java
osc.registerListener(new MyIntParameterListener(), "MyIntParameter");
```

### Getting the current value of a parameter
If you build your `VRCOsc` using `.withCacheEnabled(true)`, it will store every incoming parameter change. You can
retrieve the last known value of a parameter using:
```Java
osc.getParameterValue(<name>)
```
This will only return a (non-`null`) value *if the parameter has changed at least once since your application has started running.*  
You can provide a default value that is instead returned when this method would otherwise return `null`:
```Java
osc.getParameterValue("MyIntParameter", 0);
```

### Alternate syntax for getting and setting the value of avatar parameters
A representation of every default parameter is provided by the `VRCAvatarParameters` class. You can use these instances
to get the values of default parameters, and the types will be taken care of for you!
```Java
boolean isPlayerAfk = osc.getParameterValue(VRCAvatarParameters.AFK);
int playerTrackingType = osc.getParameterValue(VRCAvatarParameters.TRACKING_TYPE);
float gestureLeftWeight = osc.getParameterValue(VRCAvatarParameters.GESTURE_LEFT_WEIGHT);
```
However, you can extend this to your own parameters as well!
```Java
public static final VRCAvatarParameter<Integer> MY_INT_PARAMETER = VRCAvatarParameters.create("MyIntParameter");
...
int myIntParameter = osc.getParameterValue(MY_INT_PARAMETER);
osc.setParameterValue(MY_INT_PARAMETER, 1); // Also supports setting the value
```

## Planned features
- Listening to parameters that *aren't* sent to the `/avatar/parameters/` path

## Want to support my work? ♥
If you like what I've made, please consider supporting me on Ko-fi!  
[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/A0A64F1MC)