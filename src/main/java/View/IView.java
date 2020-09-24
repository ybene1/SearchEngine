package View;

import ViewModel.Controller;
import javafx.stage.Stage;

/**
 * this is the interface for the view of the UI
 */
public interface IView {

    void setController(Controller controller, Stage primaryStage);
}
