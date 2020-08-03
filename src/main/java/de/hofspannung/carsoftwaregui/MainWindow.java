package de.hofspannung.carsoftwaregui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MainWindow extends BorderPane {

  public MainWindow() {
    var loader = new FXMLLoader(getClass().getResource("mainwindow.fxml"));
    loader.setRoot(this);
    loader.setController(this);

    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
