package com.example.fadlou_restaurant;

import javafx.scene.control.Alert;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Home implements Serializable {
    private final Menu menu;
    private final User admin = new User();
    private final List<SelectedItem> selectedItems;
    private final TreeMap<LocalDate, List<HistoryItem>> salesHistory;

    private final List<Table> tables;

    public Home() {
        this.menu = new Menu();
        this.selectedItems = new ArrayList<SelectedItem>();
        this.salesHistory = new TreeMap<>();
        this.tables = new ArrayList<>();
    }

    public List<SelectedItem> getSelectedItems() {
        return selectedItems;
    }

    public void selectItem(String str) {
        for (String name : menu.getMenu().keySet()) {
            if (name.equals(str)) {
                selectedItems.add(new SelectedItem(menu.getMenu().get(name), 1));
            }
        }

    }

    public boolean signIn(String name, String password) {
        if (admin.getName().equals(name) && admin.getPassword().equals(password)) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Informations invalides");
            alert.setContentText("SVP Entrez un valide nom d'utilisateur et mot de passe");
            alert.showAndWait();
            return false;
        }
    }

    public void save() {

        ObjectOutputStream out;

        try {

            out = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    "restaurant.dat")));

            out.writeObject(this);

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public TreeMap<LocalDate, List<HistoryItem>> getSalesHistory() {
        return salesHistory;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void addSale(LocalDate dateTime, HistoryItem historyItem) {
        List<HistoryItem> historyItemList = new ArrayList<>();
        historyItemList.add(historyItem);
        if (!salesHistory.containsKey(dateTime)) {
            salesHistory.put(dateTime, historyItemList);
        } else {
            List<HistoryItem> oldItems = new ArrayList<>();
            oldItems.addAll(salesHistory.get(dateTime));
            oldItems.addAll(historyItemList);
            salesHistory.replace(dateTime, oldItems);
        }
    }


}



