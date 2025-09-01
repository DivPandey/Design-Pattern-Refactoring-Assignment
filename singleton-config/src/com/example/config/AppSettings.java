package com.example.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Proper Singleton: private constructor, thread-safe lazy initialization,
 * reflection protection, and serialization safety.
 */
public class AppSettings implements Serializable {
    private static volatile AppSettings instance;
    private static final Object lock = new Object();
    private final Properties props = new Properties();

    // Private constructor to prevent direct instantiation
    private AppSettings() {
        // Prevent reflection-based instantiation
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the singleton instance");
        }
    }

    // Thread-safe lazy initialization using double-checked locking
    public static AppSettings getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AppSettings();
                }
            }
        }
        return instance;
    }

    public void loadFromFile(Path file) {
        try (InputStream in = Files.newInputStream(file)) {
            props.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String get(String key) {
        return props.getProperty(key);
    }

    // Prevent serialization from creating duplicate instances
    private Object readResolve() {
        return getInstance();
    }
}
