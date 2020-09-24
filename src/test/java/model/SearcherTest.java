package model;

import javafx.geometry.Pos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class SearcherTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parseToPostingLine() {
        String postingLineToParse= "yuval&4,5,entity|4,6,entity";
       // Searcher s = new Searcher();
      //  s.search(postingLineToParse);
    }


    public PostingLine parseToPostingLine(String postingLineToParse){
        ArrayList<PostingRecord> postingRecordArrayList = new ArrayList<>();

        int index = postingLineToParse.indexOf("&");
        String nameOfTerm = postingLineToParse.substring(0,index);
        postingLineToParse=postingLineToParse.substring(index+1);
        String[] arrayOfLine = postingLineToParse.split("[|]");
        ArrayList<PostingRecord> recordArrayList = new ArrayList<>();
        TermType termType = TermType.word;
        for (String s:
                arrayOfLine) {
            String[] parseRecord = s.split(",");
            termType = TermType.valueOf(parseRecord[2]);
            recordArrayList.add(new PostingRecord(parseRecord[0],Integer.parseInt(parseRecord[1]),termType));
        }
        return new PostingLine(nameOfTerm,recordArrayList,termType);

    }
    @Test
    public void parseToPostingLineTest (){

       PostingLine postingLine=  parseToPostingLine("laid&FBIS3-67,1,word|FBIS3-72,1,word|FBIS3-74,1,word|");
     //   System.out.println(postingLine);
        System.out.println(postingLine.toString().equals("laid&FBIS3-67,1,word|FBIS3-72,1,word|FBIS3-74,1,word|"));

        PostingLine postingLine1 = parseToPostingLine("01-04&FBIS3-65,1,date|FBIS3-69,1,date|FBIS3-72,3,date|FBIS3-74,3,date|");

        System.out.println(postingLine1.toString().equals("01-04&FBIS3-65,1,date|FBIS3-69,1,date|FBIS3-72,3,date|FBIS3-74,3,date|"));

        PostingLine postingLine2 =parseToPostingLine("analysis&FBIS3-58,2,word|FBIS3-60,1,word|FBIS3-62,1,word|FBIS3-67,4,word|FBIS3-72,1,word|FBIS3-74,1,word|");
        System.out.println(postingLine2.toString().equals("analysis&FBIS3-58,2,word|FBIS3-60,1,word|FBIS3-62,1,word|FBIS3-67,4,word|FBIS3-72,1,word|FBIS3-74,1,word|"));

    }


    public String getDataOfTermFromPosting(String line) {
        int index = line.indexOf("&");
        return line.substring(index+1);
    }

    @Test
    public void getDataOfTermFromPostingTest (){

        String postingLine=  ("laid&FBIS3-67,1,word|FBIS3-72,1,word|FBIS3-74,1,word|");
        System.out.println(getDataOfTermFromPosting(postingLine));


        String postingLine1 = ("01-04&FBIS3-65,1,date|FBIS3-69,1,date|FBIS3-72,3,date|FBIS3-74,3,date|");

        System.out.println(getDataOfTermFromPosting(postingLine1));


        String postingLine2 =("analysis&FBIS3-58,2,word|FBIS3-60,1,word|FBIS3-62,1,word|FBIS3-67,4,word|FBIS3-72,1,word|FBIS3-74,1,word|");
        System.out.println(getDataOfTermFromPosting(postingLine2));

    }


    public String mergeTwoPostingLineStrings(String seekLower, String seekUpper) {
        return seekLower + getDataOfTermFromPosting(seekUpper);
    }

    @Test
    public void mergeTwoPostingLineStringsTest(){
        System.out.println(mergeTwoPostingLineStrings("lack&FBIS3-58,2,word|FBIS3-60,1,word|FBIS3-67,2,word|FBIS3-72,1,word|FBIS3-74,1,word|FBIS3-81,1,word|", "LACK&FBIS3-58,2,word|FBIS3-72,1,word|FBIS3-74,1,word|"));
    }



    public void loadDictionaryWithPtr() {
        Map<String,Long> dictionaryWithPtr = new HashMap<>();
        FileInputStream fis = null;
        try {
            String path = "C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\DictionaryPTR.txt";
            fis = new FileInputStream(path);
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

    private void loadDictionaryTotal() {
        HashMap<String, AtomicInteger> dictionaryTotal = new HashMap<>();
        FileInputStream fis = null;
        try {
            String path = "C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\Dictionary.txt";

            fis = new FileInputStream(path);

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
     * this function load to the memory the dictionary with ptr
     */
    public void loadDictionaryEntityWithPtr() {
        Map<String,Long> dictionaryWithPtr = new HashMap<>();
        FileInputStream fis = null;
        try {
            String path = "C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\entity\\DictionaryPTR.txt";
            fis = new FileInputStream(path);
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
        Indexer.entityDictionaryWithPtr = dictionaryWithPtr;
    }

@Test
    public void seekWordFromPosTest() {
    System.out.println(seekWordFromPos("ADDITIONALLY MARKOV" ,"C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\entity\\entity.txt" ,"C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\0.txt" ));
    System.out.println(seekWordFromPos("429.8","C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\entity\\entity.txt" ,"C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\0.txt" ));
    System.out.println(seekWordFromPos("zones","C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\entity\\entity.txt" ,"C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\0.txt" ));
    System.out.println(seekWordFromPos("ZYUGANOV ZYUGANOV","C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\entity\\entity.txt" ,"C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\0.txt" ));


}


    public String seekWordFromPos(String term ,String pathToEntityMergeFiles ,String pathToMergeFiles ){


            long pos;
            String pathToSearch;
            try {
                if(Indexer.entityDictionaryWithPtr.containsKey(term)){ //This is entity
                    pos = Indexer.entityDictionaryWithPtr.get(term);
                    pathToSearch=pathToEntityMergeFiles;
                }
                else if(Indexer.dictionaryTotal.containsKey(term)){ //If it not entity maybe its regular term
                    pos = Indexer.dictionaryWithPtr.get(term);
                    pathToSearch=pathToMergeFiles;
                }
                else { //We do not have this word in the indexer
                    return null;
                }
                RandomAccessFile raf = new RandomAccessFile(pathToSearch, "r");
                raf.seek(pos);
                return (raf.readLine());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }




@Test
    public void searchTermsTest() {
        loadDictionaryWithPtr();
        loadDictionaryEntityWithPtr();
        loadDictionaryTotal();
        String Query = "British channel impact";
        ArrayList<Term> terms = new ArrayList<>();
        terms.add(new Term(TermType.word,"BRITISH"));
        terms.add(new Term(TermType.word,"channel"));
        terms.add(new Term(TermType.entity, "BRITISH CHANNEL"));
        terms.add(new Term(TermType.word, "impact"));
        ArrayList<PostingLine> postingLines = searchTerms(terms,"C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\entity\\entity.txt" ,"C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\0.txt" );
        System.out.println(postingLines);
    }


    public ArrayList<PostingLine> searchTerms(ArrayList<Term> terms,String pathToEntityMergeFiles,String pathToMergeFiles){
        ArrayList<PostingLine> postingLines = new ArrayList<>();
        for (Term term : terms
                ) {
            if (Indexer.dictionaryTotal.containsKey(term.getNameAfter())) {
                if (term.getTermType().equals(TermType.word)) {
                    if (Indexer.dictionaryTotal.containsKey(term.getNameAfter().toUpperCase()) &&
                            Indexer.dictionaryTotal.containsKey(term.getNameAfter().toLowerCase())) {
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



}