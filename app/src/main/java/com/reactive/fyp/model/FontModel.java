package com.reactive.fyp.model;

import java.io.Serializable;

public class FontModel implements Serializable {
    String fontName;
    String Price;

    public FontModel(String fontName, String price) {
        this.fontName = fontName;
        Price = price;
    }

    public FontModel() {
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
