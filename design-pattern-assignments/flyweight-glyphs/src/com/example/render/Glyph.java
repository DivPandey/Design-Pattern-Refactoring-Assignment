package com.example.render;

public class Glyph {
    // Smell: style stored per instance → not memory efficient
    private final char ch;
    private final TextStyle style;

    public Glyph(char ch, TextStyle style) {
        this.ch = ch; this.style = style;
    }

    public int drawCost() { return style.getSize() + (style.isBold() ? 10 : 0); }
    public char getCh() { return ch; }
    public String getFont() { return style.getFont(); }
    public int getSize() { return style.getSize(); }
    public boolean isBold() { return style.isBold(); }
}
