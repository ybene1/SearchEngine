package model;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * this is class represents the saving term to disk
 */
public class SaveTermToDisk {

    private static SaveTermToDisk instance;
    private volatile AtomicInteger runIndex = new AtomicInteger();
    private volatile String pathDestination;


    /**
     * this static function creates one single tone instance of this class
     * @param pathDestination
     * @return saveTermToDisk instance
     */
    public static synchronized SaveTermToDisk getInstance(String pathDestination) {
        if(instance==null) {
            instance = new SaveTermToDisk(pathDestination);
        }
        return instance;
    }

    /**
     * setter for the path dest
     * @param pathDestination
     */
    public void setPathDestination(String pathDestination) {
        this.pathDestination = pathDestination;
        initFolders();
    }

    public void setRunIndex(AtomicInteger runIndex) {
        this.runIndex = runIndex;
    }

    /**
     * this is the constructor of this class
     * @param pathDestination
     */
    private SaveTermToDisk(String pathDestination){
        this.pathDestination = pathDestination;
        runIndex = new AtomicInteger(0);
        initFolders();
    }

    /**
     * this function make the directories for the entity files
     */
    private void initFolders() {
        String folder = this.pathDestination+"\\";
        File entity = new File(folder+"entity");
        entity.mkdir();
    }

    /**
     * this function gets map and write it to the disk with saveMapToDisk function
     * @param termsPosting
     */
    public void mainFunctionSaveTerm(Map<String, PostingLine> termsPosting){
        String path = pathDestination +'\\'+ runIndex.getAndIncrement()+".txt";
        saveMapToDisk(termsPosting,path);
    }

    /**
     * this function writes the map to a file in the disk - all the terms without entity
     * @param termsPosting
     * @param path
     */
    private void saveMapToDisk(Map<String,PostingLine> termsPosting, String path) {
        File f = new File(path);
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            List<String> keys = new ArrayList<>(termsPosting.keySet());
            Collections.sort(keys);
            for (String key : keys) {
                writer.write(termsPosting.get(key).toString() + '\n');
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * this function writes the map to a file in the disk- entity terms
     * @param entityPosting
     */
    public void entityFunctionSaveTerm(Map<String, PostingLine> entityPosting) {
        String path = pathDestination +'\\'+ "entity\\"+ runIndex.getAndIncrement()+".txt";
        saveMapToDisk(entityPosting,path);
    }
}