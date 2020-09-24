package model;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

/**
 * this class represent the Model
 */
public class Model extends Observable implements IModel {

    private ReadParseExecutor readParseExecutor;
    private String destPath;
    private String srcPath;
    private int numsOfTreads = 4;
    private boolean stemming;
    private Engine engine;
    private String queryString;
    private String queryPath;

    /**
     * the constructor of the Model
     */
    public Model() {
        this.readParseExecutor = new ReadParseExecutor(numsOfTreads, null, null);
        this.stemming = false;
        engine = new Engine();
    }

    /**
     * this is the main function that running the ReadParseExecutor.
     */
    public void start() {
        if (validPath(destPath) && validPath(srcPath)) {
            this.readParseExecutor.setDestPath(destPath);
            this.readParseExecutor.setSrcPath(srcPath);
            readParseExecutor.run(srcPath+"\\corpus");
            setChanged();
            notifyObservers();
        }
    }

    /**
     * setter for the destination path
     * @param text
     */
    public void setDestPath(String text) {
        this.destPath = text;
        setChanged();
        notifyObservers();
    }

    /**
     * the getter for the number of documents
     * @return the number of documents
     */
    @Override
    public int getNumberOfDocs() {
        return readParseExecutor.getNumOfDoc();
    }

    /**
     * the getter for the number of terms
     *@return the number of terms
     */
    @Override
    public int getNumberOfTerms() {
        return readParseExecutor.getNumberOfTerms();
    }



    /**
     * setter for the source path
     * @param text
     */
    public void setSrcPath(String text) {
        this.srcPath = text;
    }


    /**
     * this function reset all the memory of this program and deletes all the files
     * that we have been write to the destination path
     * @param path
     */
    @Override
    public void reset(String path) {
        if (path != null) {
            resetAll(path);
        }
        setChanged();
        notifyObservers();
    }


    /**
     * this function reset all the memory of this program and deletes all the files
     * that we have been write to the destination path
     * @param path
     */
    private void resetAll(String path) {
        File file1 = new File(path + "\\stemming");
        File file2 = new File(path + "\\withoutStemming");
        if (validPath(file1.getAbsolutePath())) {
            try {
                FileUtils.deleteDirectory(file1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (validPath(file2.getAbsolutePath())) {
            try {
                FileUtils.deleteDirectory(file2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        resetMemory();
    }


    /**
     * this function reset the memory of this program
     */
    public void resetMemory() {
        if (Indexer.dictionaryDF != null && Indexer.dictionaryTotal != null && Indexer.documentInfoMap != null &&
                Indexer.dictionaryWithPtr != null && Indexer.entityDictionaryWithPtr != null && Indexer.numberOfTerms != null) {
            Indexer.dictionaryDF.clear();
            Indexer.dictionaryTotal.clear();
            Indexer.documentInfoMap.clear();
            Indexer.dictionaryWithPtr.clear();
            Indexer.entityDictionaryWithPtr.clear();
            Indexer.numberOfTerms.set(0);
            this.readParseExecutor = new ReadParseExecutor(numsOfTreads, null, null);
            SaveTermToDisk.getInstance(destPath).setPathDestination(destPath);
        }

    }

    /**
     * this function displays the dictionary (term , frequency)
     * @return array list of the term , frequency as string.
     */
    @Override
    public ArrayList<String> displayDictionary() {
        this.readParseExecutor.setDestPath(destPath);
        return readParseExecutor.displayDictionary();
    }

    /**
     * this function load the dictionary from the disk to the memory
     */
    @Override
    public void loadDictionary() {
        this.readParseExecutor.setDestPath(destPath);
        if(readParseExecutor.dictionaryFilesExists()){
            readParseExecutor.loadDictionary();
        }
        else {
            setChanged();
            notifyObservers("load");
        }
    }


    /**
     * this function sets the source path
     * @param file
     */
    public void loadFileForSrcPath(File file) {
        if (validPath(file.getAbsolutePath())) {
            srcPath = file.getAbsolutePath();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * this function sets the destination path
     * @param file
     */
    public void loadFileForDestPath(File file) {
        if (validPath(file.getAbsolutePath())) {
            destPath = file.getAbsolutePath();
        }
        setChanged();
        notifyObservers();
    }

    /**
     * this function sets the stemming flag at the read parse exceutor
     * @param flag
     */
    public void stemming(boolean flag) {
        this.readParseExecutor.setWithStemming(flag);
        stemming = flag;
    }

    /**
     * this function check if the path is valid
     * @param pathToCheck
     * @return true if the path is valid
     */
    public boolean validPath(String pathToCheck) {
        File directory = new File(pathToCheck);
        if (!directory.exists()) {
            return false;
        }
        return true;
    }

    //partB
    @Override
    public void loadFileForQuery(File file) {
        if(validPath(file.getAbsolutePath())){
            engine.setQueryPath(file.getAbsolutePath());
        }
    }

    @Override
    public ArrayList<String> runSingleQuery() {
        engine.setQueryString(queryString);
        engine.setDestPath(destPath);
        return engine.runSearchSingleQuery(stemming);
    }

    @Override
    public HashMap<String, ArrayList<String>> runQueriesFilePath()  {
        this.engine.setQueryPath(queryPath);
        engine.setDestPath(destPath);
        return engine.runSearchFromQueriesFile(stemming);
    }

    @Override
    public void loadFileForSaveResults(File file) {

    }


    @Override
    public void setSemantic(boolean selected) {
        engine.setSemantic(selected);
    }

    @Override
    public void entitySearch(boolean selected) {
        engine.setSearchEntity(selected);
    }

    @Override
    public void setQueryString(String text) {
        this.queryString = text;
    }

    @Override
    public void setQueryPath(String text) {
        this.queryPath = text;
    }



}