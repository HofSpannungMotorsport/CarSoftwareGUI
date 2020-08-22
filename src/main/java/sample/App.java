package sample;

import de.hofspannung.carsoftware.registry.RegistryBuilder;
import de.hofspannung.carsoftware.registry.RegistryController;
import de.hofspannung.carsoftwaregui.MainWindow;
import de.hofspannung.carsoftwaregui.controls.EntryControl;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

  public static void main(String[] args) {
    // Enable Asserts
    // Asserts should not be used in productions, I do know this. It will be fixed at some later point.
    ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);

    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    var file = new File(
        "D:\\Programmieren\\HofSpannung\\CarSoftwareGUI\\src\\main\\resources\\de\\hofspannung\\carsoftware\\registry\\default.json");
    var file2 = new File(
        "D:\\Programmieren\\HofSpannung\\CarSoftwareGUI\\src\\main\\resources\\de\\hofspannung\\carsoftware\\registry\\default2.json");

    VBox root = FXMLLoader.load(getClass().getResource("sample.fxml"));

    var builder = new RegistryBuilder(file);
    var reg = builder.build();

    reg.getEntries().forEach(e -> {
      root.getChildren().add(new EntryControl(e));
    });

    var controller = new RegistryController();
    controller.buildFromFile(file);
    controller.buildFromFile(file2);

    primaryStage.setTitle("CarSoftware GUI - HofSpannung Motorsport e.V.");
    var scene = new Scene(new MainWindow(controller), 600, 400);
    primaryStage.setScene(scene);
    primaryStage.show();


  }
}
