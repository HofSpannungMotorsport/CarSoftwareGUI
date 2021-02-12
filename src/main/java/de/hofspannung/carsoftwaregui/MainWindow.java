package de.hofspannung.carsoftwaregui;

import com.fazecast.jSerialComm.SerialPort;
import de.hofspannung.carsoftware.registry.RegistryController;
import de.hofspannung.carsoftwaregui.controls.RegistryTab;
import java.io.IOException;
import java.util.Collection;
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
  private TabPane registries;

  @FXML
  private ChoiceBox<SerialPort> ports;

  @FXML
  private Button connect;
  @FXML
  private Button disconnect;

  private RegistryController controller;

  public MainWindow() {
    var loader = new FXMLLoader(getClass().getResource("mainwindow.fxml"));
    loader.setRoot(this);
    loader.setController(this);

    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    ports.setConverter(
        new StringConverter<>() {
          @Override
          public String toString(SerialPort serialPort) {
            if (serialPort == null) {
              return "----";
            }
            return serialPort.getDescriptivePortName();
          }

          @Override
          public SerialPort fromString(String s) {
            return null;
          }
        });
  }

  public void setPorts(Collection<SerialPort> ports) {
    this.ports.getItems().clear();
    this.ports.getItems().addAll(ports);
  }

  public void setController(@NotNull RegistryController controller) {
    registries.getTabs().clear();

    this.controller = controller;

    controller.getTypes().forEach(registryType -> {
      registries.getTabs().add(new RegistryTab(controller.getRegistry(registryType)));
    });
  }
}
