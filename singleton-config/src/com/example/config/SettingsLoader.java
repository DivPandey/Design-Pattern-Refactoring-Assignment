package com.example.config;

import java.nio.file.Path;

/** Thin wrapper that uses the singleton instance. */
public class SettingsLoader {
    public AppSettings load(Path file) {
        AppSettings settings = AppSettings.getInstance(); // Use singleton instance
        settings.loadFromFile(file);
        return settings;
    }
}
