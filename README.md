# Xperia AOD Dynamic Smth

A minimal Android Studio prototype that tests drawing **Hello Wssorld** over Sony Xperia's built-in Ambient display (AOD) while leaving Sony's clock and notifications enabled.

## What this tests

There is no public Android API for inserting third-party content into Sony's native Ambient display renderer. Xperia Help Guides for some models describe selecting a Sticker/photo in **Settings → Appearance → Ambient display (Always-on display)**; that is a Sony-provided setting, not a documented third-party integration API.

This prototype uses an Android `AccessibilityService` and its `TYPE_ACCESSIBILITY_OVERLAY` window, a mechanism used by existing AOD customizer apps. The overlay is transparent and non-touchable, so native clock/notifications remain visible and touch input is not intercepted. It is added when Android reports that the screen turns off and removed when the screen turns on.

**Device behavior is not guaranteed.** Android's accessibility overlay API permits drawing over screen windows, but Sony's AOD/Doze renderer may hide third-party windows on a given Xperia model or software version. This app does not disable or replace the native AOD. If the message does not appear during AOD, that is evidence this overlay route is blocked on that device; it cannot be fixed by ordinary app permissions alone.

## Build

1. Open this repository in Android Studio (Giraffe or newer).
2. Let Gradle sync. The project uses Android Gradle Plugin 8.7.3 and Kotlin 2.0.21.
3. Run the `app` configuration on an Xperia with Ambient display enabled.

## Try it

1. Open **Xperia AOD Overlay Test**.
2. Tap **Enable overlay service** and enable the service in Android Accessibility settings.
3. Keep Sony's Ambient display enabled in system settings.
4. Lock the phone and wait for AOD. The prototype requests an accessibility overlay containing **Hello Wssorld**.
5. Wake/unlock the phone; the overlay is removed.

The service deliberately does not request access to window text/content, gestures, or touch interaction. Android will show an accessibility-service disclosure because that is how this prototype obtains the privileged overlay window type. Enable it only if you understand and want to test this behavior. This proof of concept is not yet suitable for Play Store distribution; accessibility services are intended for accessibility functionality and Google Play has additional policy requirements.

## Project structure

- `app/src/main/java/com/redstoneinvente/xperiaaod/MainActivity.kt`: setup screen.
- `app/src/main/java/com/redstoneinvente/xperiaaod/AodOverlayService.kt`: screen-state receiver and transparent overlay.
- `app/src/main/res/xml/aod_overlay_accessibility_service.xml`: minimal service declaration.
- `app/src/main/AndroidManifest.xml`: application and service registration.

## Compatibility

Minimum Android version: Android 8.0 (API 26). Build target: Android 15 (API 35). The Xperia model and Android version are unspecified; test behavior on-device before extending the prototype.
