module com.example.javainterpretorgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javainterpretorgui to javafx.fxml;
    exports com.example.javainterpretorgui;
}