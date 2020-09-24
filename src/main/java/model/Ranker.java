package model;

import java.util.*;

public class Ranker {
    private String destPath;
    private ArrayList<PostingLine> postingLines;

    public Ranker(String destPath) {
        this.destPath = destPath;
    }


    /**
     * this function ranks the score between each query to the documents that the query appears.
     * @param postingLines
     * @param termArrayList
     * @return sorted array list of docid by their relevant to the specific query
     */
    public ArrayList<String> rank(ArrayList<PostingLine> postingLines, ArrayList<Term> termArrayList) {
        double k1 = 1.2;
        double b = 0.3;
        int sum = 0;
        // the number of documents in the corpus - documentInfo size of rows
        // need to find the number of documents that has this term - the size of the array list at the
        int numberOfDocs = Indexer.documentInfoMap.size();
        for (DocumentInfo document : Indexer.documentInfoMap.values()) {
            sum += document.getSizeOfDoc();
        }
        int avgLengthOfAllCorpusDocs = sum / numberOfDocs;
        // DOCID, score
        HashMap<String, Double> results = new HashMap<>();
        HashMap<String, Integer> queryTF = new HashMap<>();
        for (Term term : termArrayList) {
            queryTF.put(term.getNameAfter(), term.getTotalTf());
        }
        // for each term we have posting line
        for (PostingLine postingLine : postingLines) {
            for (int i = 0; i < postingLine.getPostingRecordArrayList().size(); i++) {
                PostingRecord postingRecord = postingLine.getPostingRecordArrayList().get(i);
                int tf = postingRecord.getTf();
                int lengthOfDocument = Indexer.documentInfoMap.get(postingRecord.getDocId()).getSizeOfDoc();
                // posting record
                int numberOfDocumentsWithThisTerm = postingLine.getPostingRecordArrayList().size();
                double IDF = Math.log10((numberOfDocs - numberOfDocumentsWithThisTerm + 0.5)
                        / (numberOfDocumentsWithThisTerm + 0.5));
                double tfScore = 0.0;
                if (queryTF.containsKey(postingLine.getNameOfTerm())) {
                    tfScore = (double) (queryTF.get(postingLine.getNameOfTerm()).intValue()) / lengthOfDocument;
                }
                double IDF1=0.0;
                if (Indexer.dictionaryDF.containsKey(postingLine.getNameOfTerm())) {
                    IDF1 = Math.log10(numberOfDocs / Indexer.dictionaryDF.get(postingLine.getNameOfTerm()).intValue());
                }
                double score = IDF * ((tf * (k1 + 1)) / (tf + k1 * (1 - b + b * (lengthOfDocument / avgLengthOfAllCorpusDocs))));
                if (!results.containsKey(postingRecord.getDocId())) {
                    results.put(postingRecord.getDocId(), score * 0.6 + tfScore * 0.9 + IDF1 * 0.2);
                } else {
                    results.put(postingRecord.getDocId(), new Double(results.get(postingRecord.getDocId()).doubleValue() + score * 0.6 + tfScore * 0.9 + IDF1 * 0.2));
                }
            }
        }
        ArrayList<String> temp = new ArrayList<>(sortByValue(results).keySet());
        ArrayList<String> result = new ArrayList<>();
        int countToFifty = 0;
        for (String s : temp) {
            if (countToFifty == 50) {
                break;
            }
            result.add(s);
            countToFifty++;
        }
        return result;
    }


    // the result is 148
//    public ArrayList<String> rank(ArrayList<PostingLine> postingLines) {
//        double k1 = 1.2;
//        double b = 0.3;
//        int sum = 0;
//        // the number of documents in the corpus - documentInfo size of rows
//        // need to find the number of documents that has this term - the size of the array list at the
//        int numberOfDocs = Indexer.documentInfoMap.size();
//        for (DocumentInfo document : Indexer.documentInfoMap.values()) {
//            sum += document.getSizeOfDoc();
//        }
//        int avgLengthOfAllCorpusDocs = sum / numberOfDocs;
//        // DOCID, score
//        HashMap<String, Double> results = new HashMap<>();
//        // for each term we have posting line
//        for (PostingLine postingLine : postingLines) {
//            for (int i = 0; i < postingLine.getPostingRecordArrayList().size(); i++) {
//                PostingRecord postingRecord = postingLine.getPostingRecordArrayList().get(i);
//                int tf = postingRecord.getTf();
//                int lengthOfDocument = Indexer.documentInfoMap.get(postingRecord.getDocId()).getSizeOfDoc();
//                // posting record
//                int numberOfDocumentsWithThisTerm = postingLine.getPostingRecordArrayList().size();
//                double IDF = Math.log10((numberOfDocs - numberOfDocumentsWithThisTerm + 0.5)
//                        / (numberOfDocumentsWithThisTerm + 0.5));
//                double IDF1 = Math.log10(numberOfDocs/Indexer.dictionaryDF.get(postingLine.getNameOfTerm()).intValue());
//                double score = IDF * ((tf * (k1 + 1)) / (tf + k1 * (1 - b + b * (lengthOfDocument / avgLengthOfAllCorpusDocs))));
//                if (!results.containsKey(postingRecord.getDocId())) {
//                    results.put(postingRecord.getDocId(), score);
//                } else {
//                    results.put(postingRecord.getDocId(), new Double(results.get(postingRecord.getDocId()).doubleValue() + score));
//                }
//            }
//        }
//        ArrayList<String> temp = new ArrayList<>(sortByValue(results).keySet());
//        ArrayList<String> result = new ArrayList<>();
//        int countToFifty = 0;
//        for (String s : temp) {
//            if(countToFifty == 50){
//                break;
//            }
//            result.add(s);
//            countToFifty++;
//        }
//        return result;
//    }

    /**
     * this function sorts hash map by their values.
     * @param hm
     * @return sorted hash map
     */
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double>> list =
                new LinkedList<>(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                if (o1.getValue() < o2.getValue()) {
                    return 1;
                } else if (o1.getValue() > o2.getValue()) {
                    return -1;
                }
                return 0;
            }
        });
        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
