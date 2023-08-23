package com.example.fadlou_restaurant;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class PlusItemController {
    private Home home;
    private DashboardController dashboardController;

    private TableMenuController tableMenuController;

    private int index;
    private boolean dashboard;
    @FXML
    private MFXTextField name;

    @FXML
    private MFXTextField prixVente;

    @FXML
    void addItem(ActionEvent event) {
        String name = this.name.getText();
        double prix = Double.parseDouble(this.prixVente.getText());
        if(dashboard){
            if(!name.isEmpty() && prix>0){
                home.getSelectedItems().add(new SelectedItem(new Element(name,null,0,prix),1));
            }
            dashboardController.getItemLayout().getChildren().clear();
            dashboardController.afficher(home.getSelectedItems());
        }else {
            if(!name.isEmpty() && prix>0){
                home.getTables().get(index).getItems().add(new SelectedItem(new Element(name,null,0,prix),1));
            }
            tableMenuController.getList().getChildren().clear();
            tableMenuController.afficher(home.getTables().get(index).getItems());
        }

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setInfo(Home home,DashboardController controller,TableMenuController controller2,int i,boolean bool){
        this.home = home;
        this.dashboardController = controller;
        this.dashboard = bool;
        this.tableMenuController = controller2;
        this.index = i;
    }
}
