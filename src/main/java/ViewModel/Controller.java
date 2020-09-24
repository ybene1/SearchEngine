package ViewModel;
import model.IModel;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Controller extends Observable implements Observer {

    private IModel model;

    /**
     * the constructor of this class
     * @param model
     */
    public Controller(IModel model){
        this.model = model;
    }

    /**
     * forwarding the file to the model to load the src path
     * @param file
     */
    public void loadFileForSrcPath(File file){
        model.loadFileForSrcPath(file);
    }

    /**
     * forwarding the file to the model to load the dest path
     * @param file
     */
    public void loadFileForDestPath(File file){
        model.loadFileForDestPath(file);
    }

    /**
     * setting the stemming value at the model
     * @param flag
     */
    public void stemming(boolean flag){
        model.stemming(flag);
    }


    public void update(Observable o, Object arg) {
        if ( o == model){
            setChanged();
            notifyObservers(arg);
        }
    }

    /**
     * starting the model start and running the program - crating inverted files
     */
    public void start(){
        model.start();
    }

    /**
     * getter to the model
     * @return model
     */
    public IModel getModel() {
        return model;
    }

    /**
     * forwarding to the model the path we want to remove all the files in
     * @param path
     */
    public void reset(String path){
        this.model.reset(path);
    }

    /**
     * forwarding to the model the request for showing the dictionary
     * @return the array list of string represnting the dictionary of term,frequency
     */
    public ArrayList<String> displayDictionary(){
        return this.model.displayDictionary();
    }


    /**
     * forwarding the request for the model to load the dictionary to the memeory
     */
    public void loadDictionary(){
        this.model.loadDictionary();
    }

    /**
     * setter for dest path
     * @param text
     */
    public void setDestPath(String text) {
        model.setDestPath(text);
    }

    /**
     * setter for src path
     * @param text
     */
    public void setSrcPath(String text) {
        model.setSrcPath(text);
    }

    /**
     * getter for number of docs
     * @return
     */
    public int getNumberOfDocs() {
        return model.getNumberOfDocs();
    }

    /**
     * getter for number of terms
     * @return
     */
    public int getNumberOfTerms() {
        return model.getNumberOfTerms();
    }

    /**
     * reset only the memory of the program
     */
    public void resetMemory() {
        model.resetMemory();
    }

    public void loadFileForQuery(File file) {
        model.loadFileForQuery(file);
    }

    public ArrayList<String> runSingleQuery() {
        return model.runSingleQuery();
    }

    public void setSemantic(boolean selected) {
        model.setSemantic(selected);
    }

    public void entitySearch(boolean selected) {
        model.entitySearch(selected);
    }

    public void setQueryString(String text) {
        model.setQueryString(text);
    }

    public void setQueryPath(String text) {
        model.setQueryPath(text);
    }

    public HashMap<String, ArrayList<String>> runQueriesFilePath() {
        return model.runQueriesFilePath();
    }

    public void loadFileForSaveResults(File file) {
        model.loadFileForSaveResults(file);
    }
}
