# Location Simulator

Android app for developer/testing use that publishes a chosen latitude/longitude as a mock GPS provider.

## Build
Open this project in Android Studio, let Gradle sync, then **Build > Build APK(s)**.

Requirements: Android Studio with Android SDK Platform 35 and a JDK supported by the Android Gradle Plugin.

## Phone setup
1. Enable Developer options.
2. Enable/select **Location Simulator** under **Select mock location app** (wording varies by Android version/OEM).
3. Open Location Simulator.
4. Enter latitude and longitude.
5. Tap Start Mock Location.

The app uses Android's test-provider API. Apps can detect mock locations, and some apps may ignore them. This project does not attempt to bypass those protections.
