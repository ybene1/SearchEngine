package View;

import ViewModel.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultsForQueryView extends View implements IView {

    public ListView<String> lv_resultDisplay;
    private Controller controller;
    private Stage stage;
    private boolean singleQuery;

    /**
     * this function sets the controllers of this class
     *
     * @param controller
     * @param stage
     */
    public void setController(Controller controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
        fillResults();
    }


    /**
     * this function fill the results observable with the list of doc numbers results from the controller
     */
    public void fillResults() {
        // single query run
        if (singleQuery) {
            ArrayList<String> result = controller.runSingleQuery();
            ArrayList<String> resultWithNumOfQuery = new ArrayList<>();
            for (String s: result){
                resultWithNumOfQuery.add("111 , "+s);
            }
            resultWithNumOfQuery.add("111   total documents:  " + resultWithNumOfQuery.size());
            ObservableList<String> observableList = FXCollections.observableArrayList(resultWithNumOfQuery);
            for (String str : observableList)
                lv_resultDisplay.getItems().add(str);
        } else {
            // multiply query run
              HashMap<String,ArrayList<String>> resultHashMap = controller.runQueriesFilePath();
            ArrayList<String> result = new ArrayList<>();
            for (String s : resultHashMap.keySet()) {
                for (String list: resultHashMap.get(s)) {
                    result.add(s+"    , "+list);
                }
                result.add(s+"  total documents:  " + resultHashMap.get(s).size());
            }
            ObservableList<String> observableList = FXCollections.observableArrayList(result);
            for (String str : observableList)
                lv_resultDisplay.getItems().add(str);
        }
    }


    public void setSingleQuery(boolean singleQuery) {
        this.singleQuery = singleQuery;
    }
}
