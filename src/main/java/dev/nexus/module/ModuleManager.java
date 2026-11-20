package dev.nexus.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void register(Module module) { modules.add(module); }

    public void registerDefaults() {
        // Combat category intentionally contains no automation in this starter build.
        register(new Module("Combat HUD", "Shows combat status indicators.", Category.COMBAT) {});
        register(new Module("Sprint", "Keeps sprint enabled when possible.", Category.MOVEMENT) {});
        register(new Module("ToggleSprint", "Sprint toggle helper.", Category.MOVEMENT) {});
        register(new Module("FullBright", "Client-side brightness helper.", Category.VISUAL) {});
        register(new Module("HUD", "Displays the NEXUS overlay.", Category.VISUAL) {});
        register(new Module("Keystrokes", "Shows movement key states.", Category.VISUAL) {});
        register(new Module("FPS", "Displays the current frame rate.", Category.VISUAL) {});
        register(new Module("Coordinates", "Displays your current coordinates.", Category.VISUAL) {});
        register(new Module("ArmorStatus", "Displays equipped armor durability.", Category.PLAYER) {});
        register(new Module("AutoTool", "Selects a suitable tool manually on request.", Category.PLAYER) {});
        register(new Module("Notifications", "Shows client-side module notifications.", Category.MISC) {});
        register(new Module("Configuration", "Client configuration utilities.", Category.MISC) {});
    }

    public void tick() { modules.forEach(Module::tick); }
    public List<Module> all() { return Collections.unmodifiableList(modules); }
    public List<Module> in(Category category) {
        return modules.stream().filter(m -> m.getCategory() == category).toList();
    }
}
