package com.example.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable Order with Builder pattern. Validates all data and prevents mutability leaks.
 */
public class Order {
    private final String id;
    private final String customerEmail;
    private final List<OrderLine> lines;
    private final Integer discountPercent; // 0..100, validated
    private final boolean expedited;
    private final String notes;

    // Private constructor - only Builder can create instances
    private Order(String id, String customerEmail, List<OrderLine> lines, 
                  Integer discountPercent, boolean expedited, String notes) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.lines = lines;
        this.discountPercent = discountPercent;
        this.expedited = expedited;
        this.notes = notes;
    }

    // Getters - no setters for immutability
    public String getId() { return id; }
    public String getCustomerEmail() { return customerEmail; }
    public List<OrderLine> getLines() { return Collections.unmodifiableList(lines); } // prevents external modification
    public Integer getDiscountPercent() { return discountPercent; }
    public boolean isExpedited() { return expedited; }
    public String getNotes() { return notes; }

    public int totalBeforeDiscount() {
        int sum = 0;
        for (OrderLine l : lines) sum += l.getQuantity() * l.getUnitPriceCents();
        return sum;
    }

    public int totalAfterDiscount() {
        int base = totalBeforeDiscount();
        if (discountPercent == null) return base;
        return base - (base * discountPercent / 100);
    }

    /**
     * Builder for creating immutable Order instances with validation.
     */
    public static class Builder {
        private String id;
        private String customerEmail;
        private List<OrderLine> lines = new ArrayList<>();
        private Integer discountPercent;
        private boolean expedited = false;
        private String notes;

        // Required parameters
        public Builder(String id, String customerEmail) {
            this.id = id;
            this.customerEmail = customerEmail;
        }

        // Required: at least one line
        public Builder addLine(OrderLine line) {
            if (line != null) {
                // Defensive copy of the OrderLine
                OrderLine copy = new OrderLine(line.getSku(), line.getQuantity(), line.getUnitPriceCents());
                this.lines.add(copy);
            }
            return this;
        }

        public Builder addLines(List<OrderLine> lines) {
            if (lines != null) {
                for (OrderLine line : lines) {
                    addLine(line);
                }
            }
            return this;
        }

        public Builder discountPercent(Integer discountPercent) {
            this.discountPercent = discountPercent;
            return this;
        }

        public Builder expedited(boolean expedited) {
            this.expedited = expedited;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        /**
         * Builds the Order with validation. Throws IllegalArgumentException if validation fails.
         */
        public Order build() {
            // Validate required fields
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Order ID is required");
            }
            
            if (customerEmail == null || !PricingRules.isValidEmail(customerEmail)) {
                throw new IllegalArgumentException("Valid customer email is required");
            }
            
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("At least one order line is required");
            }
            
            // Validate discount
            if (!PricingRules.isValidDiscount(discountPercent)) {
                throw new IllegalArgumentException("Discount must be between 0 and 100");
            }

            // Create defensive copy of lines list
            List<OrderLine> defensiveLines = new ArrayList<>();
            for (OrderLine line : lines) {
                defensiveLines.add(new OrderLine(line.getSku(), line.getQuantity(), line.getUnitPriceCents()));
            }

            return new Order(id, customerEmail, defensiveLines, discountPercent, expedited, notes);
        }
    }
}
