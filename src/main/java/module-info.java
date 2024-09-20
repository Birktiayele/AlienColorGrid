module com.example.proj3_alientitles {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.proj3_alientitles to javafx.fxml;
    exports com.example.proj3_alientitles;
}