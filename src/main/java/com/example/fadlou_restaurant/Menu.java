package com.example.fadlou_restaurant;

import java.io.Serializable;
import java.util.TreeMap;

public class Menu implements Serializable {
    private final TreeMap<String, Element> menu;

    public Menu() {
        this.menu = new TreeMap<String, Element>();
    }

    public TreeMap<String, Element> getMenu() {
        return menu;
    }

    public boolean addElement(String name, Type type, double price, double price2) {
        Element element = new Element(name, type, price, price2);
        if (!menu.containsKey(name)) {
            menu.put(name, element);
            return true;
        } else return false;
    }

    public boolean deleteElement(String name) {
        if (menu.containsKey(name)) {
            menu.remove(name);
            return true;
        } else return false;
    }

    public void editElement(Element element, String name, Type type, double price) {
        if (name != null) {
            element.setName(name);
        }
        if (type != null) {
            element.setType(type);
        }
        if (price != 0) {
            element.setPrixAchat(price);
        }
    }
}
