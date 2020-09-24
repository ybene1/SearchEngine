package model;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class is the interface of the Model
 */
public interface IModel {
    void loadFileForSrcPath(File file);
    void loadFileForDestPath(File file);
    void stemming(boolean flag);
    void start();
    void reset(String path);
    ArrayList<String> displayDictionary();
    void loadDictionary();
    void setSrcPath(String text);
    void setDestPath(String text);
    void resetMemory();
    int getNumberOfDocs();
    int getNumberOfTerms();

    void loadFileForQuery(File file);

    ArrayList<String> runSingleQuery();

    void setSemantic(boolean selected);

    void entitySearch(boolean selected);

    void setQueryString(String text);

    void setQueryPath(String text);

    HashMap<String, ArrayList<String>> runQueriesFilePath();

    void loadFileForSaveResults(File file);
}
