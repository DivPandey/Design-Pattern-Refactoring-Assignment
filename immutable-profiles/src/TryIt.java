import com.example.profiles.*;

public class TryIt {
    public static void main(String[] args) {
        ProfileService svc = new ProfileService();
        
        // Create minimal profile using service
        UserProfile p1 = svc.createMinimal("u1", "a@b.com");
        System.out.println("Minimal profile: " + p1.getId() + " - " + p1.getEmail());
        
        // Create complete profile using Builder directly
        UserProfile p2 = new UserProfile.Builder("u2", "john@example.com")
            .displayName("John Doe")
            .phone("123-456-7890")
            .address("123 Main St")
            .marketingOptIn(true)
            .twitter("@johndoe")
            .github("johndoe")
            .build();
        
        System.out.println("Complete profile: " + p2.getDisplayName() + " - " + p2.getEmail());
        
        // Demonstrate immutability - this will cause compilation error
        // p2.setEmail("evil@example.com"); // This line would not compile!
        System.out.println("Profile is immutable - no setters available!");
        
        // Create profile with display name using service
        UserProfile p3 = svc.createWithDisplayName("u3", "jane@example.com", "Jane Smith");
        System.out.println("Profile with display name: " + p3.getDisplayName());
        
        System.out.println("=> All profiles are now immutable and created using Builder pattern.");
    }
}
