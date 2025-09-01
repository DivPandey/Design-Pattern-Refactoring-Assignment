import com.example.orders.*;

public class TryIt {
    public static void main(String[] args) {
        OrderLine l1 = new OrderLine("A", 1, 200);
        OrderLine l2 = new OrderLine("B", 3, 100);
        
        // Using the new Builder pattern
        Order o = new Order.Builder("o2", "a@b.com")
                .addLine(l1)
                .addLine(l2)
                .discountPercent(10)
                .build();
        
        System.out.println("Before: " + o.totalAfterDiscount());
        l1.setQuantity(999); // This should NOT affect the order total anymore
        System.out.println("After:  " + o.totalAfterDiscount());
        System.out.println("=> In the solution, totals remain stable due to defensive copies.");
        
        // Demonstrate validation
        try {
            Order invalidOrder = new Order.Builder("", "invalid-email")
                    .addLine(l1)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        try {
            Order invalidOrder = new Order.Builder("o3", "valid@email.com")
                    .addLine(l1)
                    .discountPercent(150) // Invalid discount
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        try {
            Order invalidOrder = new Order.Builder("o4", "valid@email.com")
                    .build(); // No lines
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        // Test email validation
        try {
            Order invalidOrder = new Order.Builder("o7", "no-at-sign")
                    .addLine(l1)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        try {
            Order invalidOrder = new Order.Builder("o8", null)
                    .addLine(l1)
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        // Test valid discount values
        try {
            Order validOrder = new Order.Builder("o5", "valid@email.com")
                    .addLine(l1)
                    .discountPercent(0) // Valid discount
                    .build();
            System.out.println("Valid order with 0% discount created successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        try {
            Order validOrder = new Order.Builder("o6", "valid@email.com")
                    .addLine(l1)
                    .discountPercent(100) // Valid discount
                    .build();
            System.out.println("Valid order with 100% discount created successfully");
        } catch (IllegalArgumentException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
