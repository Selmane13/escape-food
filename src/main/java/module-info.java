module com.example.fadlou_restaurant {
    requires javafx.controls;
    requires javafx.fxml;
    requires  MaterialFX;
    requires com.jfoenix;


    opens com.example.fadlou_restaurant to javafx.fxml;
    exports com.example.fadlou_restaurant;
}