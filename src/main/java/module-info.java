module org.example.laneuronaintranquila {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.laneuronaintranquila to javafx.fxml;
    exports org.example.laneuronaintranquila;
}