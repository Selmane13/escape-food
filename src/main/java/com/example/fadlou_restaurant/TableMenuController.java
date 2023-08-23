package com.example.fadlou_restaurant;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class
TableMenuController implements Initializable {

    private Home home;

    private DashboardController dashboardController;
    private int index;
    @FXML
    private VBox list;
    @FXML
    private Label total;
    @FXML
    private Accordion menu;

    public VBox getList() {
        return list;
    }

    @FXML
    void annuler(ActionEvent event) {
        home.getTables().get(index).setVide(true);
        home.getTables().get(index).getItems().removeAll(home.getTables().get(index).getItems());
        list.getChildren().clear();
        sumItems();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        this.dashboardController.getTableList().getChildren().clear();
        this.dashboardController.afficherTables();
    }

    @FXML
    void valider(ActionEvent event) {
        home.getTables().get(index).setVide(true);
        home.addSale(LocalDate.now(), new HistoryItem(home.getTables().get(index).getItems(), LocalDate.now(), Double.parseDouble(total.getText())));
        home.getTables().get(index).getItems().removeAll(home.getTables().get(index).getItems());
        list.getChildren().clear();
        sumItems();
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        this.dashboardController.getTableList().getChildren().clear();
        this.dashboardController.afficherTables();
    }

    public void afficher(List<SelectedItem> selectedItems) {
        for (int i = 0; i < selectedItems.size(); i++) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("Item.fxml"));
            try {
                Pane pane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setTableMenuController(this);
                itemController.setInfo(home, selectedItems.get(i).getElement().getName(), selectedItems.get(i).getElement().getPrixVente(), selectedItems.get(i).getQuanity(), index, false);
                list.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        sumItems();


    }

    public void sumItems() {
        List<SelectedItem> selectedItems = this.home.getTables().get(index).getItems();
        double sum = 0;
        double price;
        int qte;
        for (int i = 0; i < selectedItems.size(); i++) {
            price = selectedItems.get(i).getElement().getPrixVente();
            qte = selectedItems.get(i).getQuanity();
            sum += price * qte;
        }
        total.setText(String.valueOf(sum));
    }

    public void setInfo(Home home, DashboardController controller, int index) {
        this.home = home;
        this.dashboardController = controller;
        this.index = index;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficher(this.home.getTables().get(index).getItems());
        drawMenu();
    }

    public void drawMenu() {
        menu.getPanes().clear();
        Set<Type> enumSet = EnumSet.of(Type.PIZZA, Type.SANDWICH, Type.TACOS, Type.HAMBURGER, Type.BOXEPOULET, Type.DESSERT, Type.BOISSON, Type.SUPPLEMENT);
        for (Type title : enumSet) {
            TitledPane pane = createTitledPane(title.toString());
            pane.getStyleClass().add("custom-titled-pane");
            pane.setStyle("-fx-background-color: #f25b0a; -fx-text-fill: white;");
            this.menu.getPanes().add(pane);
        }
    }

    public TitledPane createTitledPane(String title) {
        MFXScrollPane scrollPane = new MFXScrollPane();
        VBox paneContent = new VBox();
        // paneContent.setStyle("-fx-background-color: #dd571c");
        if (!home.getMenu().getMenu().isEmpty()) {
            for (String name : home.
                    getMenu().getMenu().keySet()) {
                if (home.
                        getMenu().getMenu().get(name).getType().equals(Type.valueOf(title))) {
                    TilePane pane = new TilePane();
                    pane.setStyle("-fx-alignment: center;-fx-pref-height: 30px;-fx-font-size: 15px; -fx-border-width: 0px 0px 2px 0px;-fx-border-color: #000000;-fx-background-color: #f6c03b");
                    pane.getChildren().add(new Text(name));
                    paneContent.getChildren().add(pane);
                    pane.setOnMouseEntered(e -> {
                        pane.setStyle("-fx-alignment: center;-fx-pref-height: 30px;-fx-font-size: 15px; -fx-border-width: 0px 0px 2px 0px;-fx-border-color: #000000;-fx-background-color: #eda316");
                    });
                    pane.setOnMouseExited(e -> {
                        pane.setStyle("-fx-alignment: center;-fx-pref-height: 30px;-fx-font-size: 15px; -fx-border-width: 0px 0px 2px 0px;-fx-border-color: #000000;-fx-background-color: #f6c03b");

                    });
                    pane.setOnMouseClicked(event -> {
                        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                            handleDoubleClick(pane);
                        }
                    });
                }
            }
        }
        paneContent.setStyle("-fx-pref-width: 410px;-fx-max-width: 410px;-fx-min-width: 410px;-fx-padding: 5px 5px 5px 5px");
        scrollPane.setContent(paneContent);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        return new TitledPane(title, scrollPane);
    }

    private void handleDoubleClick(TilePane pane) {
        for (String name : home.getMenu().getMenu().keySet()) {
            if (name.equals(((Text) pane.getChildren().get(0)).getText())) {
                home.getTables().get(index).getItems().add(new SelectedItem(home.getMenu().getMenu().get(name), 1));
            }
        }
        list.getChildren().clear();
        afficher(home.getTables().get(index).getItems());
        sumItems();

    }

    @FXML
    void plusItem(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("plusItem.fxml"));

            loader.setControllerFactory(param -> {
                PlusItemController controller = new PlusItemController();
                controller.setInfo(home, null,this,index,false);
                return controller;

            });

            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
