package View;

import ViewModel.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

/**
 * this class is the view of this program
 */
public class View implements IView, Observer {

    public javafx.scene.control.Button btn_start;

    public javafx.scene.control.Button btn_reset;
    public javafx.scene.control.Button btn_showDictionary;
    public javafx.scene.control.Button btn_loadDictionary;
    public javafx.scene.control.Button btn_browseDestPath;
    public javafx.scene.control.Button btn_browseSrcPath;
    public javafx.scene.control.TextField text_srcPath;
    public javafx.scene.control.TextField text_destPath;
    public javafx.scene.control.CheckBox cb_stemming;

    private Controller controller;
    private boolean reset;
    private Stage primaryStage;
    private boolean lastRunStem;
    private boolean lastRunWithoutStem;
    private boolean lastLoadWithStem;

    //partB
    public javafx.scene.control.Button btn_run;
    public javafx.scene.control.Button btn_browseQueriesFilePath;
    public javafx.scene.control.CheckBox cb_semantic;
    public javafx.scene.control.CheckBox cb_entitySearch;
    public javafx.scene.control.TextField text_query;
    public javafx.scene.control.TextField text_queryPath;
    public javafx.scene.control.Button btn_browseSaveResults;
    public javafx.scene.control.TextField text_saveResultsPath;
    private boolean dictionaryLoadedToMemory;


    /**
     * this function sets the controllers of this class
     *
     * @param controller
     * @param primaryStage
     */
    public void setController(Controller controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
        this.btn_reset.setDisable(false);
        lastRunStem = false;
        lastRunWithoutStem = false;
        this.dictionaryLoadedToMemory = false;
        this.lastLoadWithStem = false;
    }


    public void update(Observable o, Object arg) {
        if (o == controller && reset) {
            alertMessage("You have reset the engine", Alert.AlertType.INFORMATION);
            reset = false;
        } else if (o == controller && arg != null && arg.equals("load")) {
            alertMessage("not valid path", Alert.AlertType.ERROR);
        }

    }

    /**
     * browser button even - opening the windows browser option
     *
     * @param actionEvent
     */
    @FXML
    public void browseOfSourcePath(ActionEvent actionEvent) {
        File file = loadFile();
        if (file != null) {
            controller.loadFileForSrcPath(file);
            text_srcPath.setText(file.getAbsolutePath());
        }

    }

    /**
     * loading file with browser button
     */
    private File loadFile() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        File file = directoryChooser.showDialog(new Stage());
        {
            if (file != null) {
                File loadFile = file.getParentFile();
                directoryChooser.setInitialDirectory(loadFile);
                String name = file.getName();
                String[] n = name.split("\\.");
                if (!n[0].isEmpty()) {
                    return file;
                }
            }
        }
        return null;
    }

    /**
     * browse button action - opening windows browse window
     *
     * @param actionEvent
     */
    @FXML
    public void browseOfDestPath(ActionEvent actionEvent) {
        File file = loadFile();
        if (file != null) {
            controller.loadFileForDestPath(file);
            text_destPath.setText(file.getAbsolutePath());
        }
    }

    /**
     * check box for stemming action, setting the right stemming value if
     * selected of not
     *
     * @param actionEvent
     */
    public void stemming(javafx.event.ActionEvent actionEvent) {
        if (cb_stemming.isSelected()) {
            controller.stemming(true);
        } else {
            controller.stemming(false);
        }
    }

    /**
     * start function starting the program by sending the paths of
     * source and destination to the model with the controller and
     * starting to make the inverted index files. (with parsing the documents)
     *
     * @param actionEvent
     */
    public void start(ActionEvent actionEvent) {
        long startTime = System.currentTimeMillis();
        if ((lastRunWithoutStem && !cb_stemming.isSelected()) ||
                (lastRunStem && cb_stemming.isSelected())
                || (lastRunStem && lastRunWithoutStem)) {
            alertMessage("Please reset the system", Alert.AlertType.INFORMATION);
            return;
        }
        if (validArgs()) {
            if (validPath(text_destPath.getText()) && validPath(text_srcPath.getText())) {
                makeDirsAndCorrectDestPath();
                controller.resetMemory();
                if (cb_stemming.isSelected()) {
                    controller.stemming(true);
                }
                this.controller.setSrcPath(this.text_srcPath.getText());
                this.controller.start();
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;
                int numOfDocs = controller.getNumberOfDocs();
                int numberOfTerms = controller.getNumberOfTerms();
                alertMessage("Number of Documents that have been indexed: " + numOfDocs + "\n" +
                        "Number of terms: " + numberOfTerms + "\n" + "Total time of the process: " + totalTime / 1000 + " seconds", Alert.AlertType.INFORMATION);
            } else {
                alertMessage("Please insert valid destination and source path", Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * makes the directories withoutStemming or stemming depending on
     * the stemming value
     */
    private void makeDirsAndCorrectDestPath() {
        if (cb_stemming.isSelected()) {
            lastRunStem = true;
            String folder = this.text_destPath.getText() + "\\stemming";
            File file = new File(folder);
            file.mkdir();
            this.controller.setDestPath(this.text_destPath.getText() + "\\stemming");
        } else {
            lastRunWithoutStem = true;
            String folder = this.text_destPath.getText() + "\\withoutStemming";
            File file = new File(folder);
            file.mkdir();
            this.controller.setDestPath(this.text_destPath.getText() + "\\withoutStemming");
        }
    }

    /**
     * this function check if the args in the source and destination paths are valids
     *
     * @return true if the args are valids
     */
    private boolean validArgs() {
        if ((text_destPath == null || text_destPath.getText().trim().isEmpty())
                && (text_srcPath == null || text_srcPath.getText().trim().isEmpty())) {
            alertMessage("Please enter both source and destination path", Alert.AlertType.ERROR);
            return false;
        } else if (text_destPath == null || text_destPath.getText().trim().isEmpty()) {
            alertMessage("Please enter the destination path", Alert.AlertType.ERROR);
            return false;
        } else if (text_srcPath == null || text_srcPath.getText().trim().isEmpty()) {
            alertMessage("Please enter the source path", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    /**
     * this function present alert message depend on the type and the message text
     *
     * @param messageText
     * @param alertType
     */
    protected void alertMessage(String messageText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(messageText);
        alert.showAndWait();
        alert.close();
    }

    /**
     * this function reset all the memory in the program and removes all the files
     * that we have created after selecting the start button
     *
     * @param actionEvent
     */
    public void reset(javafx.event.ActionEvent actionEvent) {
        if (text_destPath == null || text_destPath.getText().trim().isEmpty()) {
            alertMessage("Please enter the destination path", Alert.AlertType.ERROR);
            return;
        }
        //if (validArgs()) {
            if(cb_stemming.isSelected()) {
                if (!validPath(text_destPath.getText() + "\\stemming")) {
                    alertMessage("No files to reset", Alert.AlertType.INFORMATION);
                    return;
                }
            }
            else { // not with stemming
                if (!validPath(text_destPath.getText() + "\\withoutStemming")) {
                    alertMessage("No files to reset", Alert.AlertType.INFORMATION);
                    return;
                }
            }
            reset = true;
            this.controller.reset(text_destPath.getText());
            this.lastRunWithoutStem = false;
            this.lastRunStem = false;
      //  }
    }

    /**
     * this function displays the dictionary on the screen
     *
     * @param actionEvent
     */
    public void displayDictionary(javafx.event.ActionEvent actionEvent) {
        if (!checkDestPathWithAlert()) {
            return;
        }
        if (!validPath(text_destPath.getText())) {
            alertMessage("Please enter valid destination path", Alert.AlertType.ERROR);
            return;
        }
        if (cb_stemming.isSelected()) {
            this.controller.setDestPath(this.text_destPath.getText() + "\\stemming");
        } else {
            this.controller.setDestPath(this.text_destPath.getText() + "\\withoutStemming");
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("/" + "DictionaryView.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The-Dictionary");
        Scene scene = new Scene(root, 450, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        alertMessage("Please wait a few seconds...", Alert.AlertType.INFORMATION);
        DictionaryView dictionaryView = fxmlLoader.getController();
        dictionaryView.setController(controller, stage);
        controller.addObserver(dictionaryView);
    }

    /**
     * this function load the dictionary to the memory
     *
     * @param actionEvent
     */
    public void loadDictionary(javafx.event.ActionEvent actionEvent) {
        if (!checkDestPathWithAlert()) {
            return;
        }
        if (!validPath(text_destPath.getText())) {
            alertMessage("Please enter valid destination path", Alert.AlertType.ERROR);
            return;
        }
        if (cb_stemming.isSelected()) {
            this.controller.setDestPath(this.text_destPath.getText() + "\\stemming");
            this.lastLoadWithStem = true;
        } else {
            this.controller.setDestPath(this.text_destPath.getText() + "\\withoutStemming");
            this.lastLoadWithStem = false;
        }
        alertMessage("Please wait a few seconds...\n if an error message appears the dictionary wasn't loaded successfully ", Alert.AlertType.INFORMATION);
        this.controller.loadDictionary();
        dictionaryLoadedToMemory = true;
    }

    /**
     * this function check if the destpath is valid
     *
     * @return true if the dest path is valid
     */
    private boolean checkDestPathWithAlert() {
        if (text_destPath == null || text_destPath.getText().trim().isEmpty()) {
            alertMessage("Please enter the destination path", Alert.AlertType.ERROR);
            return false;
        }
        if (cb_stemming.isSelected()) {
            if (!validPath(text_destPath.getText() + "\\stemming\\DictionaryStem.txt")) {
                alertMessage("Please enter valid destination path", Alert.AlertType.ERROR);
                return false;
            }
            return true;
        } else {
            if (!validPath(text_destPath.getText() + "\\withoutStemming\\Dictionary.txt")) {
                alertMessage("Please enter valid destination path", Alert.AlertType.ERROR);
                return false;
            }
            return true;
        }
    }


    /**
     * this function check if the directory is exists
     *
     * @param s
     * @return true if the path is correct which means that the directory have been
     * open
     */

    private boolean validPath(String s) {
        File directory = new File(s);
        if (!directory.exists()) {
            return false;
        }
        return true;
    }


    // PART B Engine

    public void run(javafx.event.ActionEvent actionEvent) {

        if(!dictionaryLoadedToMemory){
            alertMessage("Please load the dictionary to the memory first.", Alert.AlertType.ERROR);
            return;
        }
        if(!checkDestPathWithAlert()){
            return;
        }
        // TODO: 1/4/2020 to check if the dest exists and the posting there exists !!!
        if(!validPathsForRunFunction() ){
            return;
        }
        if((!lastLoadWithStem && this.cb_stemming.isSelected()) ||
                (lastLoadWithStem && !this.cb_stemming.isSelected())){
            alertMessage("Please Load The Correct Dictionary", Alert.AlertType.ERROR);
            return;
        }
        // creating search with single query
        else if ((text_queryPath == null || text_queryPath.getText().trim().isEmpty())) {
            // TODO: 1/3/2020 check if we need to verify the template of query
            controller.setQueryString(text_query.getText());
            controller.setDestPath(text_destPath.getText());
            runHelperAndShowResults(true);
        }
        // creating search with file of queries
        else if ((text_query == null || text_query.getText().trim().isEmpty())) {
            if (!validPath(text_queryPath.getText())) {
                alertMessage("Please insert valid path of queries file", Alert.AlertType.ERROR);
            } else {
                // TODO: 1/4/2020 chane the name of the query as the fourum says
                controller.setQueryPath(text_queryPath.getText()+"\\queries 03.txt");
                controller.setDestPath(text_destPath.getText());
                runHelperAndShowResults(false);
            }
        }
    }

    private boolean validPathsForRunFunction() {
        if ((text_queryPath == null || text_queryPath.getText().trim().isEmpty()) &&
                (text_query == null || text_query.getText().trim().isEmpty())) {
            alertMessage("Please enter queries file path OR any single query", Alert.AlertType.ERROR);
            return false;
        }
        else if(!(text_queryPath == null || text_queryPath.getText().trim().isEmpty()) &&
                !(text_query == null || text_query.getText().trim().isEmpty())){
            alertMessage("Please enter queries file path OR any single query, not BOTH!", Alert.AlertType.ERROR);
            return false;
        }
        else if (text_destPath == null || text_destPath.getText().trim().isEmpty()) {
            alertMessage("Please enter the destination path", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void runHelperAndShowResults(boolean singleQuery) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;
        try {
            root = fxmlLoader.load(getClass().getResource("/" + "ResultsForQueryView.fxml").openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("The-Results");
        Scene scene = new Scene(root, 450, 650);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ResultsForQueryView resultsForQueryView = fxmlLoader.getController();
        resultsForQueryView.setSingleQuery(singleQuery);
        resultsForQueryView.setController(controller,stage);
        controller.addObserver(resultsForQueryView);

    }

    public void browseOfQueriesFilePath(ActionEvent actionEvent) {
        File file = this.loadFile();
        if (file != null) {
            controller.loadFileForQuery(file);
            text_queryPath.setText(file.getAbsolutePath());
        }
    }

    public void semantic() {
        this.controller.setSemantic(cb_semantic.isSelected());
    }

    public void entitySearch() {
        this.controller.entitySearch(cb_entitySearch.isSelected());
    }

    public void save(){
        if ((text_saveResultsPath == null || text_saveResultsPath.getText().trim().isEmpty())){
            alertMessage("Please insert path to save the results", Alert.AlertType.ERROR);
            return;
        }
        else if(!checkDestPathWithAlert()){
            return;
        }
        else{
            if (cb_stemming.isSelected()) {
                copyFileUsingStream(new File(text_destPath.getText()+"\\stemming\\results.txt"),
                        new File(text_saveResultsPath.getText()+"\\results.txt"));
            }
            else{
                copyFileUsingStream(new File(text_destPath.getText()+"\\withoutStemming\\results.txt"),
                        new File(text_saveResultsPath.getText()+"\\results.txt"));
            }
        }
    }

    private static void copyFileUsingStream(File source, File dest) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void browseSaveResults(){
        File file = loadFile();
        if (file != null) {
            controller.loadFileForSaveResults(file);
            text_saveResultsPath.setText(file.getAbsolutePath());
        }
    }
}

