# Locator Bar Fix 1.0.0

A small, client-side Fabric experiment for Minecraft Java 1.21.11, designed for use alongside OneClient/VanillaHUD. Restores missing vanilla waypoint markers when the normal locator marker renderer was skipped. It does not render a second XP bar or change any other HUD element, keybind, packet, gameplay function or config. If the normal renderer completes, the fallback does nothing. F1, spectator mode, alternate cameras and mounted players are respected conservatively. No server-provided waypoints means no fallback. It cannot fix a resource pack that makes markers transparent, a renderer that runs but draws incorrectly, or a server that disables the locator bar.

## Install
1. Close Minecraft. In OneClient open the 1.21.11 instance folder and open its `mods` folder.
2. Put `locatorbarfix-1.21.11-1.0.0.jar` there. Use Fabric Loader 0.18.1 or later and Java 21. No additional Fabric API dependency is needed for this mod.
3. Launch and test with a second player on a server/world that sends vanilla waypoints. If markers still do not appear, check the VanillaHUD setting for drawing locator waypoints over XP and provide latest.log plus the exact OneClient version.
4. To undo, delete only this jar and restart.

## Build from source
Install JDK 21 and Gradle 9.2.1 (https://gradle.org/releases/), then run `gradle clean build` in this project folder. The output is in `build/libs/`. Alternatively push the source to GitHub and run the included Build Fabric mod workflow; download its Actions artifact. A Gradle wrapper is not bundled.

## Verification and version scope
The supplied experimental jar was compiled with Java 21 against locally generated signature-only stubs using verified 1.21.11 intermediary mappings, with no stubs included in the final jar. The normal Loom project is provided for a dependency-backed build. The actual game, Fabric/Mixin transformation and OneClient were not available for runtime testing here. No guarantee of compatibility. Future Minecraft versions (including 26.x) are not declared compatible: they require a mapping/API port and testing. Do not force the metadata to accept other versions.

## Test checklist
- Normal vanilla locator markers: no double rendering.
- OneClient with missing markers: server-provided markers restored when vanilla call was skipped.
- No waypoints, F1, spectator, mount, alternate camera: no extra markers.
- Compare hotbar, XP/level, health, hunger, armor, scoreboard, chat, inventory, crosshair and OneClient HUD editor before/after.
- Check latest.log for Mixin/compatibility errors. If it fails, remove the jar.
