package com.example.fadlou_restaurant;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoryItem implements Serializable {
    private final List<SelectedItem> items;
    private final LocalDate dateTime;
    private final double total;

    public HistoryItem(List<SelectedItem> items, LocalDate dateTime, double total) {
        this.dateTime = dateTime;
        this.total = total;
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    public List<SelectedItem> getItems() {
        return items;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public double getTotal() {
        return total;
    }
}
