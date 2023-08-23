package com.example.fadlou_restaurant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HistoryItemController {
    @FXML
    private Text cmd;

    @FXML
    private Text date;

    private DailyHistoryController dailyHistoryController;
    private AllHistoryController allHistoryController;
    private LocalDate dateTime;
    private boolean all;
    private final List<SelectedItem> selectedItems = new ArrayList<>();
    private Home home;
    private double total;

    private int index;

    public void setSelctedItems(List<SelectedItem> list) {

        this.selectedItems.addAll(list);

    }

    @FXML
    void consulter(ActionEvent event) {
        if (all) {
            consulterAll();
        } else {
            consulterDaily();
        }
    }

    public void consulterAll() {
        this.allHistoryController.setDate(date.getText());
        this.allHistoryController.setTotalVendu(Double.toString(calculerTotal(this.dateTime)));
        this.allHistoryController.setProfit(Double.toString(calculerProfit()));
        this.allHistoryController.setCurrDate(this.dateTime);
    }

    public void consulterDaily() {
        this.dailyHistoryController.getHistoryItem().getChildren().clear();

        for (int i = 0; i < this.selectedItems.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Item2.fxml"));
            try {
                Pane pane = fxmlLoader.load();
                Item2Controller itemController = fxmlLoader.getController();
                itemController.setInfo(home, selectedItems.get(i).getElement().getName(), selectedItems.get(i).getElement().getPrixVente(), selectedItems.get(i).getQuanity(), i, true);
                this.dailyHistoryController.getHistoryItem().getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        this.dailyHistoryController.getTotal().setText(String.valueOf(this.total));
        this.dailyHistoryController.getProfit().setText(String.valueOf(this.calculerProfit2()));
    }

    public void setHistoryController(DailyHistoryController dailyHistoryController) {
        this.dailyHistoryController = dailyHistoryController;
    }

    public void setInfo(String cmd, LocalDate date, boolean all, int index) {
        this.cmd.setText(cmd);
        this.date.setText(date.toString());
        this.dateTime = date;
        this.all = all;
        this.index = index;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setAllHistoryController(AllHistoryController allHistoryController) {
        this.allHistoryController = allHistoryController;
    }

    public double calculerTotal(LocalDate date) {
        List<HistoryItem> historyItems = home.getSalesHistory().get(date);
        double sum = 0;
        for (int i = 0; i < historyItems.size(); i++) {
            sum += historyItems.get(i).getTotal();
        }
        return sum;
    }

    public double calculerProfit() {
        double profit = 0;
        List<HistoryItem> historyItems = home.getSalesHistory().get(dateTime);
        for (int i = 0; i < historyItems.size(); i++) {
            for (int j = 0; j < historyItems.get(i).getItems().size(); j++) {
                profit += historyItems.get(i).getItems().get(j).getElement().getProfit() * historyItems.get(i).getItems().get(j).getQuanity();
            }
        }
        return profit;
    }

    public double calculerProfit2() {
        double profit = 0;
        List<SelectedItem> selectedItemList = home.getSalesHistory().get(dateTime).get(index).getItems();
        for (int j = 0; j < selectedItemList.size(); j++) {
            profit += selectedItemList.get(j).getElement().getProfit() * selectedItemList.get(j).getQuanity();
        }
        return profit;
    }
}
