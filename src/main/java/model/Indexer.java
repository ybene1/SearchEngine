package model;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.exit;

/**
 * this class represent the Indexer that responsible to make the posting files
 * and save them to the disk
 */
public class Indexer implements Serializable {
    public static volatile ConcurrentHashMap<String, AtomicInteger> dictionaryDF = new ConcurrentHashMap<>(); //The amount of documents in which a particular TERM appeared
    public static volatile ConcurrentHashMap<String, AtomicInteger> dictionaryTotal = new ConcurrentHashMap<>(); //The number of times a TERM appeared in the documents
    public static volatile ConcurrentHashMap<String, DocumentInfo> documentInfoMap = new ConcurrentHashMap<>(); //Dictionary with the data on the documents
    // after merge dictionary
    public static volatile Map<String, Long> dictionaryWithPtr; //Pointer to the row of the TERM on disk
    public static volatile Map<String, Long> entityDictionaryWithPtr;
    public static volatile AtomicInteger numberOfTerms = new AtomicInteger(0); //Number of saved words
    public static volatile HashMap<String, List<Pair<String, Integer>>> invertedDocEntity = new HashMap<>();
    private String postingPath; //Path to posting file
    //Temporary data structures for terms
    private Map<String, DictionaryEntry> termsDictionary; // Temporary TERM dictionary for current iteration for words
    private Map<String, PostingLine> termsPosting; // A dictionary that collects the posting record for a particular TERM
    //Temporary data structures for entity
    private Map<String, DictionaryEntry> entityDictionary;// Temporary ENTITY dictionary for current iteration for words
    private Map<String, PostingLine> entityPosting; // A dictionary that collects the posting record for a particular ENTITY



    /**
     * An empty constructor that initializes the data structures
     */
    public Indexer() {
        dictionaryWithPtr = new HashMap<>();
        entityDictionaryWithPtr = new HashMap<>();
        initializeDataStructers();
    }

    /**
     * Getter to posting files
     *
     * @return Path to the posting files
     */
    public String getPostingPath() {
        return postingPath;
    }

    /**
     * Setter to the posting path
     *
     * @param postingPath Path to the posting files
     */
    public void setPostingPath(String postingPath) {
        this.postingPath = postingPath;
    }

    /**
     * Getter to temporary terms dictionary
     *
     * @return terms dictionary
     */
    public Map<String, DictionaryEntry> getTermsDictionary() {
        return termsDictionary;
    }

    /**
     * Getter to temporary terms posting dictionary
     *
     * @return terms posting dictionary
     */
    public Map<String, PostingLine> getTermsPosting() {
        return termsPosting;
    }

    /**
     * Saves the data for the documents to disk
     *
     * @param destPath Path to the data retention place
     */
    public static void saveDocInfoToDisk(String destPath) {
        System.out.println("the size of doc info dictionary is: "+Indexer.documentInfoMap.size());
        ConcurrentHashMap<String, DocumentInfo> toSave = new ConcurrentHashMap<>(documentInfoMap);
        System.out.println("the size of to save dictionary is: "+toSave.size());
        StringBuilder t = new StringBuilder();
        t.append(destPath);
        t.append("\\DictionaryDocInfo.txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(t.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(toSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Saves the data for the DF of TERMS to disk
     *
     * @param destPath Path to the data retention place
     */
    public static void saveTermDFToDisk(String destPath) {
        ConcurrentHashMap<String, AtomicInteger> toSave = dictionaryDF; //The amount of documents in which a particular TERM appeared
        StringBuilder t = new StringBuilder();
        t.append(destPath);
        t.append("\\DictionaryTermDF.txt");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(t.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(toSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create an inverted index for the words in documents
     *
     * @param documentArrayList List of documents that the indexer indexes in each iteration
     */
    public void createInvertedIndex(ArrayList<Document> documentArrayList) {
        for (int i = 0; i < documentArrayList.size(); i++) {
            Document tempDoc = documentArrayList.get(i);
            int maxTf = 0;
            for (Term t : // An indexing of all words from a particular document to a subsequent temporary data structure will write to disk
                    tempDoc.getListOfTerms()) {
                addTermToDictionaryType(tempDoc.getDOCNO(), t);
                if (t.getTotalTf() > maxTf) { //Check who is the most common word in a specific document
                    maxTf = t.getTotalTf();
                }
            }
            for (Term e : // Writing whatever it may be a temporary dictionary entity to write on disk
                    tempDoc.getListOfEntity()) {
                addEntityToDictionary(tempDoc.getDOCNO(), e);
            }
            insertToDocumentInfoMap(tempDoc, maxTf); // Insert the document data into the document dictionary that we must save at the end of the disk
        }
        SaveTermToDisk saveTermToDisk = SaveTermToDisk.getInstance(postingPath); // saveTermToDisk is Singleton
        saveAllPosting(saveTermToDisk); //Save the temporary data to disk
    }

    /**
     * Add the entity to the temporary dictionaries
     *
     * @param docID The document in which the entity appeared
     * @param e     Entity type
     */
    private void addEntityToDictionary(String docID, Term e) {
        DictionaryEntry temp = entityDictionary.get(e.getNameAfter());
        PostingRecord PostingRecord = new PostingRecord(docID, e.getTotalTf(), e.getTermType());
        if (temp != null) { // Already exist in the entity dictionary
            temp.addOneMoreDocument(); //#id ++
            temp.updateTf(e.getTotalTf());
            PostingLine postingLine = entityPosting.get(e.getNameAfter());
            ArrayList<PostingRecord> postingRecordList = postingLine.getPostingRecordArrayList();
            postingRecordList.add(PostingRecord);
        } else if (temp == null) { // Not exist in the dictionary
            ArrayList<PostingRecord> postingRecordArrayList = new ArrayList<PostingRecord>();
            postingRecordArrayList.add(PostingRecord);
            PostingLine postingLine = new PostingLine(e.getNameAfter(), postingRecordArrayList, e.getTermType());
            entityPosting.put(e.getNameAfter(), postingLine);
            DictionaryEntry dic = new DictionaryEntry(1, e.getTotalTf(), e.getTermType());
            entityDictionary.put(e.getNameAfter(), dic);
        }
    }

    /**
     * Save all posting files to disk
     *
     * @param saveTermToDisk Instance of Save Term To Disk
     */
    private void saveAllPosting(SaveTermToDisk saveTermToDisk) {
        saveTermToDisk.mainFunctionSaveTerm(this.termsPosting);
        saveTermToDisk.entityFunctionSaveTerm(this.entityPosting);
    }

    /**
     * Updates the document dictionary
     *
     * @param doc   Object of Document
     * @param maxTf The amount of word impressions that appeared the most
     */
    private void insertToDocumentInfoMap(Document doc, int maxTf) {
        DocumentInfo documentInfo = new DocumentInfo(doc);
        documentInfo.setMaxTf(maxTf); //Changes the value of TF according to what is observed in the indexer
        documentInfoMap.put(doc.getDOCNO(), documentInfo);
    }

    /**
     * Initialize data stricters of Indexer
     */
    public void initializeDataStructers() {
        termsDictionary = new HashMap<String, DictionaryEntry>();
        termsPosting = new HashMap<String, PostingLine>();
        entityPosting = new HashMap<String, PostingLine>();
        entityDictionary = new HashMap<String, DictionaryEntry>();

    }

    /**
     * Add the TERM to the temporary dictionaries.
     * @param docID The document in which the entity appeared
     * @param termToAdd A word that appeared in the document
     */
    private void addTermToDictionaryType(String docID, Term termToAdd) {
        if (termToAdd.getNameAfter() == null) {
            exit(-1);
        }
        DictionaryEntry temp = termsDictionary.get(termToAdd.getNameAfter());
        PostingRecord PostingRecord = new PostingRecord(docID, termToAdd.getTotalTf(), termToAdd.getTermType());
        if (temp != null) { // Already exist in the dictionary
            temp.addOneMoreDocument(); //#id ++
            temp.updateTf(termToAdd.getTotalTf());
            PostingLine postingLine = termsPosting.get(termToAdd.getNameAfter());
            ArrayList<PostingRecord> postingRecordList = postingLine.getPostingRecordArrayList();
            postingRecordList.add(PostingRecord);
        } else if (temp == null) { // Not exist in the dictionary
            ArrayList<PostingRecord> postingRecordArrayList = new ArrayList<PostingRecord>();
            postingRecordArrayList.add(PostingRecord);
            PostingLine postingLine = new PostingLine(termToAdd.getNameAfter(), postingRecordArrayList, termToAdd.getTermType());
            termsPosting.put(termToAdd.getNameAfter(), postingLine);
            DictionaryEntry dic = new DictionaryEntry(1, termToAdd.getTotalTf(), termToAdd.getTermType());
            termsDictionary.put(termToAdd.getNameAfter(), dic);
        }
        dictionaryTotal.computeIfAbsent(termToAdd.getNameAfter(), s -> new AtomicInteger(0)).addAndGet(termToAdd.getTotalTf());
        dictionaryDF.computeIfAbsent(termToAdd.getNameAfter(), s -> new AtomicInteger(0)).addAndGet(1);
    }

    /**
     * Save the dictionary of all of the terms to the disk
     * @param path The path on the disk to which the data will be saved
     * @param withStemming If the parser do stemming or not
     */
    public static void saveDictionaryToDisk(String path, boolean withStemming) {
        HashMap<String, AtomicInteger> after = bigAndSmallLetters();
        numberOfTerms.set(after.size());
        StringBuilder t = new StringBuilder();
        t.append(path);
        if (withStemming) {
            t.append("\\DictionaryStem.txt");
        } else {
            t.append("\\Dictionary.txt");
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(t.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.writeObject(after);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Do this after the indexer.
     * Checks whether a word is save as lowercase or uppercase
     * @return HashMap=Dictionary. Dictionary after reduction of uppercase and lowercase letters.
     * If a word has a capital letter and a small letter then it is saved as a small letter.
     */
    private static HashMap<String, AtomicInteger> bigAndSmallLetters() {
        HashMap<String, AtomicInteger> after = new HashMap<>();
        for (String s : dictionaryTotal.keySet()) {
            // if you are a big letters
            if (Character.isUpperCase(s.charAt(0))) {
                if (s.matches("^([A-Z]*)$")) {
                    // need to save as lower
                    if (dictionaryTotal.containsKey(s.toLowerCase())) {
                        after.computeIfAbsent(s.toLowerCase(), k -> new AtomicInteger(0)).addAndGet(dictionaryTotal.get(s).intValue());
                    } else {
                        after.computeIfAbsent(s, k -> new AtomicInteger(0)).addAndGet(dictionaryTotal.get(s).intValue());
                    }
                    // need to save as upper
                } else {
                    after.computeIfAbsent(s, k -> new AtomicInteger(0)).addAndGet(dictionaryTotal.get(s).intValue());
                }
            } else {
                after.computeIfAbsent(s, k -> new AtomicInteger(0)).addAndGet(dictionaryTotal.get(s).intValue());
            }
        }
        return after;
    }
}