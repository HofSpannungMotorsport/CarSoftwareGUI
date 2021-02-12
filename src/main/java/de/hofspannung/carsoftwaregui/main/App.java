package de.hofspannung.carsoftwaregui.main;

import com.fazecast.jSerialComm.SerialPort;
import de.hofspannung.carsoftwaregui.MainWindow;
import java.util.Arrays;
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
    var main = new MainWindow();
    var scene = new Scene(main, 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();

    main.setPorts(Arrays.asList(SerialPort.getCommPorts()));

    // load registries

    // link registries with connection
    // through a new controller?
  }
}