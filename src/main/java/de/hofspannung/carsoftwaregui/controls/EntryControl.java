package de.hofspannung.carsoftwaregui.controls;

import de.hofspannung.carsoftware.data.number.Float;
import de.hofspannung.carsoftware.data.number.Int32;
import de.hofspannung.carsoftware.data.number.Number;
import de.hofspannung.carsoftware.registry.Entry;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.jetbrains.annotations.NotNull;

public class EntryControl extends VBox {

  private final static float floatStep = 0.01f;
  private final static int intStep = 1;

  private final Entry<?> entry;
  private final SpinnerValueFactory<Number> factory = new SpinnerValueFactory<>() {

    @Override
    public void decrement(int i) {
      entry.getValue().subtract(makeStep(i));
    }

    @Override
    public void increment(int i) {
      entry.getValue().add(makeStep(i));
    }

    private Number makeStep(int i) {
      var step = entry.getValue().isFloatingPoint() ? new Float(floatStep) : new Int32(intStep);
      step.multiply(new Int32(i));
      return step;
    }
  };

  @FXML
  private Spinner<Number> spinner;
  @FXML
  private Slider slider;

  @FXML
  private Label name;
  @FXML
  private Label unit;
  @FXML
  private Label min;
  @FXML
  private Label max;

  public EntryControl(Entry<?> entry) {
    this.entry = entry;

    var loader = new FXMLLoader(getClass().getResource("entrycontrol.fxml"));
    loader.setRoot(this);
    loader.setController(this);

    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    bind();
    spinner.setValueFactory(factory);
    factory.setConverter(new StringConverter<>() {
      @Override
      public String toString(Number number) {
        return number.toString();
      }

      @Override
      public Number fromString(String s) {
        var val = entry.getValue();
        val.parse(s);
        return val.clone();
      }
    });
    slider.valueProperty().addListener((o, b, a) -> sliderChanged());
  }

  private void bind() {

    name.setText(entry.getName());
    unit.setText(entry.getUnit());

    min.setText(entry.getMin().toString());
    max.setText(entry.getMax().toString());

    slider.setMin(entry.getMin().doubleValue());
    slider.setMax(entry.getMax().doubleValue());

    slider.setValue(entry.getValue().doubleValue());

    factory.setValue(entry.getValue().clone());

    entry.addValueChangedListener(this::valueChanged);
  }

  private void valueChanged(Entry<?> entry, @NotNull Number number) {
    factory.setValue(number.clone());
    slider.setValue(number.doubleValue());
    System.out.println("change: " + number);
  }

  private void sliderChanged() {
    entry.getValue().cast(slider.getValue());
  }

}
