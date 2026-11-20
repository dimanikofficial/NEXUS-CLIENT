# NEXUS CLIENT

Purple-accented, dark two-column Fabric ClickGUI starter for Minecraft 1.21.11.

## Get a ready JAR without installing development tools

1. Create a GitHub account if you do not already have one.
2. Create a new **public** repository named `NEXUS-CLIENT`.
3. Upload the contents of this project ZIP to the repository (not the ZIP file itself).
4. Open the repository's **Actions** tab.
5. Select **Build NEXUS CLIENT**, then press **Run workflow** (or wait for the first push build).
6. When the run finishes successfully, open it and download the artifact named `NEXUS-CLIENT-Minecraft-1.21.11`.
7. Extract the downloaded artifact ZIP. Put the `.jar` inside `.minecraft/mods`.

The included GitHub Actions workflow installs Java 21 and Gradle in the cloud and builds the mod for you.

## Local build (optional)

Requirements: Java 21 and Gradle. Run `gradle build`; the mod JAR appears in `build/libs/`.

## In game

Install Fabric Loader and Fabric API for Minecraft 1.21.11. Press **Right Shift** to open/close the ClickGUI. Use the left navigation rail to select a category and click a module row to toggle it.

## Notes

This is an early GUI/module framework. Module switches currently toggle framework state; they do not all implement in-game effects. This starter does not include combat automation or server-evasion features.
