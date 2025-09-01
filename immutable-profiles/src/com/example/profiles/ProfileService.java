package com.example.profiles;

/**
 * Service for creating immutable UserProfile objects using Builder pattern.
 */
public class ProfileService {

    /**
     * Creates a minimal immutable profile with required fields only.
     */
    public UserProfile createMinimal(String id, String email) {
        return new UserProfile.Builder(id, email).build();
    }

    /**
     * Creates a complete profile with all fields using Builder pattern.
     */
    public UserProfile createComplete(String id, String email, String phone, 
                                    String displayName, String address, 
                                    boolean marketingOptIn, String twitter, String github) {
        return new UserProfile.Builder(id, email)
            .phone(phone)
            .displayName(displayName)
            .address(address)
            .marketingOptIn(marketingOptIn)
            .twitter(twitter)
            .github(github)
            .build();
    }

    /**
     * Creates a profile with display name (validates and trims if needed).
     */
    public UserProfile createWithDisplayName(String id, String email, String displayName) {
        // Validate and trim display name before building
        if (displayName != null && displayName.length() > 100) {
            displayName = displayName.substring(0, 100);
        }
        
        return new UserProfile.Builder(id, email)
            .displayName(displayName)
            .build();
    }
}
