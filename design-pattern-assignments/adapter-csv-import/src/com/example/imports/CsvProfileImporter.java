package com.example.imports;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class CsvProfileImporter implements ProfileImporter {
    private final NaiveCsvReader reader;
    private final ProfileService profileService;

    public CsvProfileImporter(NaiveCsvReader reader, ProfileService profileService) {
        this.reader = Objects.requireNonNull(reader, "reader");
        this.profileService = Objects.requireNonNull(profileService, "profileService");
    }

    @Override
    public int importFrom(Path csvFile) {
        Objects.requireNonNull(csvFile, "csvFile");
        List<String[]> rows = reader.read(csvFile);
        int success = 0;
        boolean first = true;
        for (String[] row : rows) {
            if (first) { // skip header if present
                first = false;
                // detect header by names
                if (row.length >= 3 &&
                    equalsIgnoreCase(row[0], "id") &&
                    equalsIgnoreCase(row[1], "email") &&
                    equalsIgnoreCase(row[2], "displayName")) {
                    continue;
                }
            }

            if (row == null || row.length < 3) {
                System.out.println("Skipping row: not enough columns");
                continue;
            }

            String id = trimToNull(row[0]);
            String email = trimToNull(row[1]);
            String displayName = row[2] != null ? row[2].trim() : null;

            if (id == null) {
                System.out.println("Skipping row: missing id");
                continue;
            }
            if (email == null || !email.contains("@")) {
                System.out.println("Skipping row: bad email");
                continue;
            }

            try {
                profileService.createProfile(id, email, displayName);
                success++;
            } catch (RuntimeException ex) {
                System.out.println("Skipping row: " + ex.getMessage());
            }
        }
        return success;
    }

    private static boolean equalsIgnoreCase(String a, String b) {
        return a != null && a.equalsIgnoreCase(b);
    }

    private static String trimToNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}


