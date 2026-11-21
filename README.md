# BR Bridger

A client-side Fabric mod for practicing Minecraft Java 26.3 bridging.

## Use

1. Hold a placeable block in your main hand.
2. Aim at a block face or edge and press `G` to enable bridge assist.
3. Move and aim normally; the mod repeatedly invokes Minecraft's usual use action, so vanilla placement rules apply.
4. Press `G` again to stop.

The key can be changed under **Options > Controls > Key Binds > BR Bridger**. Assist automatically stops if the main hand no longer contains a block item or when you leave the world. Player movement and camera aiming remain manual.

## Build

Install a Java 25 JDK, then run:

```sh
./gradlew build
```

The mod JAR is written to `build/libs/br-bridger-1.0.0.jar`. Install it in a Fabric 26.3 profile together with Fabric API.
