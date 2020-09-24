package model;
import javafx.util.Pair;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Searcher {
    private boolean callAPI;
    private boolean optForEntitySearch;
    private String destPath;

    public Searcher(boolean callAPI, boolean optForEntitySearch, String destPath) {
        this.callAPI = callAPI;
        this.optForEntitySearch = optForEntitySearch;
        this.destPath = destPath;
    }


    /**
     * This function returns a String list - each record represents
     * a document+":"+ the list of entities that appear in it
     *
     * @param docs A list of the document names for them to retrieve the
     *             five entities that appear the most
     * @return if the dictionary of doc-Entity is null return empty list
     */
    public ArrayList<String> getListOfDocsAndThe5MostCommonEntity(List<String> docs) {
        ArrayList<String> result = new ArrayList<>();
        //   HashMap<String, List<Pair<String, Integer>>> docAndEntity = SearchForEntitiesInADocument();
        if (Indexer.invertedDocEntity != null && Indexer.invertedDocEntity.size() != 0) {
            for (int i = 0; i < docs.size(); i++) {
                String toAdd = docs.get(i);
                if (Indexer.invertedDocEntity.containsKey(toAdd)) {
                    List<Pair<String, Integer>> list = Indexer.invertedDocEntity.get(toAdd);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(toAdd);
                    stringBuilder.append(":");
                    for (int j = 0; j < list.size(); j++) {
                        stringBuilder.append(" ");
                        stringBuilder.append(list.get(j).getKey());
                        stringBuilder.append(":");
                        stringBuilder.append(list.get(j).getValue());
                        stringBuilder.append(",");
                    }
                    result.add(stringBuilder.toString());
                }
                else{
                    result.add(toAdd);
                }
            }
            return result;
        }
        return new ArrayList<>();
    }


    /**
     * This function returns a dictionary that maps a document to the five entities that appear the most
     */
//    private HashMap<String, List<Pair<String, Integer>>> SearchForEntitiesInADocument(){
//        HashMap<String, List<Pair<String, Integer>>> invertedDocEntity = new HashMap<>();
//        FileInputStream fileInputStream = null;
//        try {
//            fileInputStream = new FileInputStream(destPath + "\\entity\\DictionaryDocEntity.txt");
//            ObjectInputStream ois = null;
//            ois = new ObjectInputStream(fileInputStream);
//            Object o = ois.readObject();
//            invertedDocEntity = (HashMap<String, List<Pair<String, Integer>>>) (o);
//            ois.close();
//        }
//    catch (FileNotFoundException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return  invertedDocEntity;
//    }
    public boolean isCallAPI() {
        return callAPI;
    }

    public void setCallAPI(boolean callAPI) {
        this.callAPI = callAPI;
    }

    public boolean isOptForEntitySearch() {
        return optForEntitySearch;
    }

    public void setOptForEntitySearch(boolean optForEntitySearch) {
        this.optForEntitySearch = optForEntitySearch;
    }

    public String getDestPath() {
        return destPath;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }


    public ArrayList<String> search(ArrayList<Term> query, String pathToEntityMergeFiles, String pathToMergeFiles) {
        Ranker ranker = new Ranker(destPath);
        ArrayList<PostingLine> postingLines = searchTerms(query, pathToEntityMergeFiles, pathToMergeFiles);
        ArrayList<String> ans = ranker.rank(postingLines,query);
        if (this.optForEntitySearch) {
            return getListOfDocsAndThe5MostCommonEntity(ans);
        }
        return ans;
    }


    /**
     * Receives a query and returns all relevant posting records
     * containing all relevant data so that we can perform rating
     * functions on the words in the query
     *
     * @param terms                  A query from the form of a list of words obtained from the same
     *                               parser we index the documents with
     * @param pathToEntityMergeFiles Path to entity posting file
     * @param pathToMergeFiles       Path to the posting file of all words without entities
     * @return Posting records we kept when indexing the corpus
     */
    private ArrayList<PostingLine> searchTerms(ArrayList<Term> terms, String pathToEntityMergeFiles, String pathToMergeFiles) {
        ArrayList<PostingLine> postingLines = new ArrayList<>();
        for (Term term : terms
        ) {
            if (Indexer.dictionaryWithPtr.containsKey(term.getNameAfter())) {
                if (term.getTermType().equals(TermType.word)) {
                    if (Indexer.dictionaryWithPtr.containsKey(term.getNameAfter().toUpperCase()) &&
                            Indexer.dictionaryWithPtr.containsKey(term.getNameAfter().toLowerCase())) {
                        String seekUpper = seekWordFromPos(term.getNameAfter().toUpperCase(), pathToEntityMergeFiles, pathToMergeFiles);
                        String seekLower = seekWordFromPos(term.getNameAfter().toLowerCase(), pathToEntityMergeFiles, pathToMergeFiles);
                        String mergedLine = null;
                        if (seekLower != null && seekUpper != null) {
                            mergedLine = mergeTwoPostingLineStrings(seekLower, seekUpper);
                        } else if (seekLower != null) {
                            mergedLine = seekLower;
                        } else if (seekUpper != null) {
                            mergedLine = seekUpper;
                        } else
                            continue;
                        postingLines.add(parseToPostingLine(mergedLine));
                    } else {
                        String seek = seekWordFromPos(term.getNameAfter(), pathToEntityMergeFiles, pathToMergeFiles);
                        if (seek != null)
                            postingLines.add(parseToPostingLine(seek));
                    }
                } else {
                    String seek = seekWordFromPos(term.getNameAfter(), pathToEntityMergeFiles, pathToMergeFiles);
                    if (seek != null)
                        postingLines.add(parseToPostingLine(seek));
                }
            }
        }
        return postingLines;
    }


    /**
     * Consolidates two records - with one represented in lowercase and the other in uppercase,
     * we consolidate the entries into one entry represented in lowercase
     *
     * @param seekLower An entry represented in lowercase
     * @param seekUpper An entry represented in uppercase
     * @return one entry represented in lowercase
     */
    private String mergeTwoPostingLineStrings(String seekLower, String seekUpper) {
        return seekLower + getDataOfTermFromPosting(seekUpper);
    }

    /**
     * Gets a string from the posting file and extracts the information except the word name
     *
     * @param line string from the posting file
     */
    private String getDataOfTermFromPosting(String line) {
        int index = line.indexOf("&");
        return line.substring(index + 1);
    }


    /**
     * Gets a string from the posting file and converts it into a postingLine object
     *
     * @param postingLineToParse string from the posting file
     * @return postingLine object
     */
    private PostingLine parseToPostingLine(String postingLineToParse) {
        int index = postingLineToParse.indexOf("&");
        String nameOfTerm = postingLineToParse.substring(0, index);
        postingLineToParse = postingLineToParse.substring(index + 1);
        String[] arrayOfLine = postingLineToParse.split("[|]");
        ArrayList<PostingRecord> recordArrayList = new ArrayList<>();
        TermType termType = TermType.word;
        for (String s :
                arrayOfLine) {
            String[] parseRecord = s.split(",");
            termType = TermType.valueOf(parseRecord[2]);
            recordArrayList.add(new PostingRecord(parseRecord[0], Integer.parseInt(parseRecord[1]), termType));
        }
        return new PostingLine(nameOfTerm, recordArrayList, termType);

    }


    /**
     * Search for a given word in files that store the information about the words we saved.
     *
     * @param term                   A word we want to look for
     * @param pathToEntityMergeFiles Path to entity posting file
     * @param pathToMergeFiles       Path to the posting file of all words without entities
     * @return The posting line as it appears in the file we saved,
     * if the word doesn't appear we return NULL
     */
    private String seekWordFromPos(String term, String pathToEntityMergeFiles, String pathToMergeFiles) {
        long pos;
        String pathToSearch;
        try {
            if (Indexer.entityDictionaryWithPtr.containsKey(term)) { //This is entity
                pos = Indexer.entityDictionaryWithPtr.get(term);
                pathToSearch = pathToEntityMergeFiles + "\\entity.txt";
            } else if (Indexer.dictionaryTotal.containsKey(term)) { //If it not entity maybe its regular term
                pos = Indexer.dictionaryWithPtr.get(term);
                pathToSearch = pathToMergeFiles + "\\merged.txt";
            } else { //We do not have this word in the indexer
                return null;
            }
            RandomAccessFile raf = new RandomAccessFile(pathToSearch, "r");
            raf.seek(pos);
            BufferedReader brRafReader = new BufferedReader(new FileReader(raf.getFD()));
            String res = ((brRafReader.readLine()));
            brRafReader.close();
            raf.close();
            return res;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void semantic(Term term) {

    }
}
