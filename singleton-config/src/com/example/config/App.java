package com.example.config;

import java.nio.file.Path;

public class App {
    public static void main(String[] args) throws Exception {
        String path = args.length > 0 ? args[0] : "app.properties";
        
        // Load settings using the singleton
        AppSettings.getInstance().loadFromFile(Path.of(path));
        
        // Demonstrate singleton behavior - all calls return the same instance
        AppSettings instance1 = AppSettings.getInstance();
        AppSettings instance2 = AppSettings.getInstance();
        
        System.out.println("app.name=" + instance1.get("app.name"));
        System.out.println("app.version=" + instance1.get("app.version"));
        System.out.println("app.description=" + instance1.get("app.description"));
        
        // Show that both references point to the same object
        System.out.println("instance1 hash=" + System.identityHashCode(instance1));
        System.out.println("instance2 hash=" + System.identityHashCode(instance2));
        System.out.println("Are they the same instance? " + (instance1 == instance2));
        
        // Demonstrate using SettingsLoader
        SettingsLoader loader = new SettingsLoader();
        AppSettings loadedSettings = loader.load(Path.of(path));
        System.out.println("Loaded settings hash=" + System.identityHashCode(loadedSettings));
        System.out.println("Same as singleton? " + (loadedSettings == instance1));
    }
}


