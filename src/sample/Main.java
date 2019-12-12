package sample;

import de.hofspannung.carsoftware.registry.Registry;
import de.hofspannung.carsoftware.registry.RegistryType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        // Enable Asserts
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);


        Registry<Integer> test = new Registry<>(RegistryType.INT32, 4);
        System.out.println(RegistryType.FLOAT.ordinal());

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
