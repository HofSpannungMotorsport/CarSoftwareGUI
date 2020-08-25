package de.hofspannung.carsoftwaregui.controls;


import de.hofspannung.carsoftware.registry.Registry;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

public class RegistryTab extends Tab {

  @FXML
  private VBox vbox;

  public RegistryTab(@NotNull Registry<?> registry) {
    var loader = new FXMLLoader(getClass().getResource("registrytab.fxml"));
    loader.setRoot(this);
    loader.setController(this);

    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    setRegistry(registry);
  }

  private void setRegistry(Registry<?> registry) {
    vbox.getChildren().clear();

    this.setText(registry.getName() + " (" + registry.getTypeName() + ")");
    registry.getEntries().forEach(entry -> vbox.getChildren().add(new EntryControl(entry)));
  }

}