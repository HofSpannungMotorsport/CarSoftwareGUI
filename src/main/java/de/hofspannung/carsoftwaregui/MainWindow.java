package de.hofspannung.carsoftwaregui;

import com.fazecast.jSerialComm.SerialPort;
import de.hofspannung.carsoftware.registry.RegistryController;
import de.hofspannung.carsoftwaregui.controls.RegistryTab;
import java.io.IOException;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

public class MainWindow extends BorderPane {

  @FXML
  private TabPane tabPane;

  @FXML
  private ChoiceBox<SerialPort> ports;

  @FXML
  private Button connect;
  @FXML
  private Button disconnect;

  private RegistryController controller;

  public MainWindow(@NotNull RegistryController controller) {
    this.controller = controller;

    var loader = new FXMLLoader(getClass().getResource("mainwindow.fxml"));
    loader.setRoot(this);
    loader.setController(this);

    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    makeTabs();
    dummyData();
  }

  private void dummyData() {
    ports.getItems().addAll(SerialPort.getCommPorts());
    ports.setConverter(new StringConverter<SerialPort>() {
      @Override
      public String toString(SerialPort serialPort) {
        if (serialPort==null)
          return "----";
        return serialPort.getDescriptivePortName();
      }

      @Override
      public SerialPort fromString(String s) {
        return null;
      }
    });
  }

  private void makeTabs() {
    controller.getTypes().forEach(registryType -> {
      tabPane.getTabs().add(new RegistryTab(controller.getRegistry(registryType)));
    });
  }
}
