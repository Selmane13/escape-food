package com.example.fadlou_restaurant;

import java.io.Serializable;

class SelectedItem implements Serializable {
    private Element element;
    private int quanity;

    public SelectedItem(Element element, int quanity) {
        this.element = element;
        this.quanity = quanity;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }
}