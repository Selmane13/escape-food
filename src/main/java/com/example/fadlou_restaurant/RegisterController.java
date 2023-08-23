package com.example.fadlou_restaurant;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private Home home;
    @FXML
    private JFXButton login;

    @FXML
    private MFXTextField name;

    @FXML
    private MFXPasswordField password;

    public void setHome(Home home) {
        this.home = home;

    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        String name = this.name.getText();
        String password = this.password.getText();


        if (home.signIn(name, password)) {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));

            loader.setControllerFactory(param -> {
                DashboardController controller = new DashboardController();
                controller.setHome(this.home);
                //controller.initializeAccordion();
                return controller;

            });

            Parent root = loader.load();


            Stage stage = (Stage) this.login.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(eventt -> {
                home.getSelectedItems().removeAll(home.getSelectedItems());
                home.save(); // Call your save() method here
            });
        }


    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                login(new ActionEvent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setOnKeyPressed(this::handleKeyPress);
        password.setOnKeyPressed(this::handleKeyPress);
    }
}
