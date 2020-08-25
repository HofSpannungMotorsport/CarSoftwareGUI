package de.hofspannung.carsoftwaregui.main;

import de.hofspannung.carsoftwaregui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("CarSoftware GUI - HofSpannung Motorsport e.V.");
    var scene = new Scene(new MainWindow(), 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}