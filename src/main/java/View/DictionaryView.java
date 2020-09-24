package View;
import ViewModel.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 * this class represent the dictionary view for the UI
 */
public class DictionaryView extends View implements IView  {


    public ListView<String> lv_dictionaryDisplay;
    private Controller controller;
    private Stage stage;

    /**
     * this function sets the controllers of this class
     * @param controller
     * @param stage
     */
    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        fillDictionary();
    }

    /**
     * this function fill the dictionary observable with the terms from the controller
     */
    public void fillDictionary() {
        ArrayList<String> result = controller.displayDictionary();
        ObservableList<String> dictionaryObservable = FXCollections.observableArrayList(result);
        for (String str : dictionaryObservable)
            lv_dictionaryDisplay.getItems().add(str);
    }
}
