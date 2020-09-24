package model;

import javafx.util.Pair;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadParseExecutor {
    private AtomicInteger numOfDoc = new AtomicInteger(0);
    ExecutorService executor;
    private ArrayList<File> filesToRead;
    private static final int NUM_OF_FILES_PER_BATCH = 16;
    private boolean withStemming;
    private String destPath;
    private String srcPath;
    private int numberOfTerms;
    // partB


    /**
     * the constructor of the ReadParseExecutor
     * @param numOfThreads
     * @param destPath
     * @param srcPath
     */
    public ReadParseExecutor(int numOfThreads,String destPath,String srcPath) {
        this.executor = Executors.newFixedThreadPool(numOfThreads);
        this.withStemming = false;
        this.destPath = destPath;
        this.srcPath = srcPath;
        this.numberOfTerms = 0;
    }

    /**
     *  setter for the withStemming field
     * @param withStemming
     */
    public void setWithStemming(boolean withStemming) {
        this.withStemming = withStemming;
    }


    /**
     * setter for the destination path
     * @param destPath
     */
    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    /**
     * getter for the number of terms
     * @return the number of terms
     */
    public int getNumberOfTerms() {
        return numberOfTerms;
    }

    /**
     * setter for the source path
     * @param srcPath
     */
    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    /**
     * this is the main function of the program
     * first we read all the corpus by using the runHelper function with
     * the readFile instance.
     * this function runs with threads -
     * we send batch of 25 folders that each folder contains file to the
     * readfile instance, and there we send it to the parser and indexer
     * @param corpusFolderPath
     */
    public void run(String corpusFolderPath) {
        filesToRead = new ArrayList<>();
        runHelper(new File(corpusFolderPath));
        if (!filesToRead.isEmpty()) {
            Runnable readFileWorker = new ReadFile(filesToRead, true, this, withStemming, destPath, srcPath);
            executor.execute(readFileWorker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        Merger merger = new Merger(destPath);
        merger.mergeQueue();

        merger = new Merger(destPath+"\\entity");
        merger.mergeQueueWithoutPtr();
        merger.entityFinal(destPath+"\\entity");
//        merger.createAndSavePTRDict(new File(destPath+"\\entity\\entity.txt"));
        Indexer.saveDictionaryToDisk(destPath,withStemming);
        numberOfTerms = Indexer.numberOfTerms.intValue();
        Indexer.saveDocInfoToDisk(destPath);
        Indexer.saveTermDFToDisk(destPath);
        merger.invertedDocAndEntity(destPath+"\\entity");
        //PartB
        savingAtPostingDirectoryTheStopWordFile();
    }


    /**
     * this function saving the stopWords file at the posting directory for partB
     */
    private void savingAtPostingDirectoryTheStopWordFile() {
        try {
//            File f = new File(destPath);
//            if(validPath(f.getParent()+"\\stop_words.txt")){
//                return;
//            }
            BufferedReader reader = new BufferedReader(new FileReader(srcPath+"\\stop_words.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter(destPath+"\\stop_words.txt"));
            String line = reader.readLine();
            while (line != null) {
                writer.write(line+"\n");
                line = reader.readLine();
            }
            reader.close();
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * this function using the readFile instance to read all the corpus by the corpus path
     * @param corpusFolderPath
     */
    private void runHelper(File corpusFolderPath) {
        for (File fileEntry : corpusFolderPath.listFiles()) {
            if (fileEntry.isDirectory()) {
                runHelper(fileEntry);
            } else {
                filesToRead.add(fileEntry);
                if (filesToRead.size() == NUM_OF_FILES_PER_BATCH) {
                    Runnable readFileWorker = new ReadFile(new ArrayList<>(filesToRead), true, this, withStemming, destPath, srcPath);
                    executor.execute(readFileWorker);
                    filesToRead = new ArrayList<>();
                }
            }

        }
    }

    /**
     * adds the argument numOfDocs to the field numOfDoc
     * @param numOfDocs
     */
    public void addToNumOFDocs(int numOfDocs) {
        numOfDoc.addAndGet(numOfDocs);
    }

    /**
     * getter for the number of documents
     * @return the number of documents
     */
    public int getNumOfDoc(){
        return numOfDoc.get();
    }

    /**
     * this function kill the executor
     */
    public void killAllAndExit(){
        executor.shutdownNow();
        System.exit(1);
    }

    /**
     * this function returns array list of the term , frequency
     * @return the array list of the term , frequency
     */
    public ArrayList<String> displayDictionary() {
        ArrayList<String> result = new ArrayList<>();
        HashMap<String,AtomicInteger> after;
        FileInputStream fis = null;
        try {
            if(withStemming){
                fis = new FileInputStream(destPath + "\\DictionaryStem.txt");
            }
            else{
                fis = new FileInputStream(destPath + "\\Dictionary.txt");

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Object o = ois.readObject();
            after = (HashMap<String,AtomicInteger>)(o);
            List<String> keys = new ArrayList<>(after.keySet());
            Collections.sort(keys);
            for (String key : keys) {
                result.add(key+" , "+after.get(key).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * this function load to the memory the all the dictionaries
     */
    public void loadDictionary(){

        loadDictionaryWithPtr();
        // stem or without stem
        loadDictionaryTotal();
        loadDictionaryDocInfo();
        loadDictionaryTermDF();
        loadDictionaryEntityWithPtr();
        loadDictionaryInvertedDocEntity();
    }

    private void loadDictionaryInvertedDocEntity() {
        HashMap<String, List<Pair<String, Integer>>> dictionaryDocEntity = new HashMap<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(destPath + "\\entity\\DictionaryDocEntity.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Object o = ois.readObject();
            dictionaryDocEntity = (HashMap<String, List<Pair<String, Integer>>>)(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // sets to the indexer the dictionary after been loaded from the disk
        Indexer.invertedDocEntity = dictionaryDocEntity;

    }

    /**
     * this function load to the memory the DictionaryEntityWithPtr
     */
    private void loadDictionaryEntityWithPtr() {
        Map<String,Long> dictionaryEntityWithPtr = new HashMap<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(destPath + "\\entity\\DictionaryPTR.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Object o = ois.readObject();
            dictionaryEntityWithPtr = (HashMap<String,Long>)(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // sets to the indexer the dictionary after been loaded from the disk
        Indexer.entityDictionaryWithPtr = dictionaryEntityWithPtr;
    }
    /**
     * this function load to the memory the DictionaryDocInfo
     */
    private void loadDictionaryDocInfo() {
        ConcurrentHashMap<String,DocumentInfo> dictionaryInfo = new ConcurrentHashMap<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(destPath + "\\DictionaryDocInfo.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Object o = ois.readObject();
            dictionaryInfo = (ConcurrentHashMap<String,DocumentInfo>)(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // sets to the indexer the dictionary after been loaded from the disk
        Indexer.documentInfoMap = dictionaryInfo;
    }
    /**
     * this function load to the memory the DictionaryTotal
     */
    private void loadDictionaryTotal() {
        HashMap<String, AtomicInteger> dictionaryTotal = new HashMap<>();
        FileInputStream fis = null;
        try {
            if(withStemming){
                fis = new FileInputStream(destPath + "\\DictionaryStem.txt");
            }
            else{
                fis = new FileInputStream(destPath + "\\Dictionary.txt");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Object o = ois.readObject();
            dictionaryTotal = (HashMap<String,AtomicInteger>)(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Indexer.dictionaryTotal = new ConcurrentHashMap<>(dictionaryTotal);
    }
    /**
     * this function load to the memory the DictionaryTermDF
     */
    private void loadDictionaryTermDF() {
        ConcurrentHashMap<String, AtomicInteger> dictionaryDF = new ConcurrentHashMap<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(destPath + "\\DictionaryTermDF.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Object o = ois.readObject();
            dictionaryDF = (ConcurrentHashMap<String,AtomicInteger>)(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Indexer.dictionaryDF = new ConcurrentHashMap<>(dictionaryDF);
    }

    /**
     * this function load to the memory the dictionary with ptr
     */
    public void loadDictionaryWithPtr() {
        Map<String,Long> dictionaryWithPtr = new HashMap<>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(destPath + "\\DictionaryPTR.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Object o = ois.readObject();
            dictionaryWithPtr = (HashMap<String,Long>)(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // sets to the indexer the dictionary after been loaded from the disk
        Indexer.dictionaryWithPtr = dictionaryWithPtr;
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
    public boolean dictionaryFilesExists(){
        if(!validPath(destPath+ "\\DictionaryPTR.txt")
                || !validPath(destPath + "\\DictionaryDocInfo.txt")||
                 !validPath(destPath + "\\entity\\DictionaryPTR.txt") ){
            return false;
        }
        return true;
    }
}
