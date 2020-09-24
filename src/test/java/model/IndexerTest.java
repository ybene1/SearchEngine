package model;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class IndexerTest {

    @Test
    public void testInsertion() {
        ArrayList<Term> arraytest = new ArrayList<Term>();
//        arraytest.add(new Term(false,"Yuval","Yuval",TermType.word));
//        arraytest.add(new Term(false,"a","a",TermType.word));
//        arraytest.add(new Term(false,"Eliezer","Eliezer",TermType.word));
//        arraytest.add(new Term(false,"3","3",TermType.number));
//        ArrayList<Term> arraytest1 = new ArrayList<Term>();
//        arraytest1.add(new Term(false,"Yuval","Yuval",TermType.word));
//        arraytest1.add(new Term(false,"b","b",TermType.word));
//        arraytest1.add(new Term(false,"Eliezer","Eliezer",TermType.word));
//        arraytest1.add(new Term(false,"3","3",TermType.number));
//        Document d1 = new Document("1","Yuval a Eliezer 3",null,4,1,1,arraytest,"tit");
//        Document d2 = new Document("2","Yuval b Eliezer 3",null,4,1,1,arraytest1,"tit");
        Indexer indexer = new Indexer();
        indexer.setPostingPath("lalala");
        ArrayList<Document> documents = new ArrayList<>();
     //   documents.add(d1);
      //  documents.add(d2);
        indexer.createInvertedIndex(documents);
        System.out.println("posting Path:"+ indexer.getPostingPath());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Dictionary:"+ indexer.getTermsDictionary());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("posting Dictionary:"+ indexer.getTermsPosting());
        }
    @Test
    public void checkDocsTestOneFile(){
    ReadParseExecutor readParseExecutor = new ReadParseExecutor(1,"","");
    readParseExecutor.run("C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\corpus\\FB396001");
  //  Indexer index = new Indexer();
    //index.saveDictionaryToDisk("C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\Engine\\PLEASEWORK");
    System.out.println(readParseExecutor.getNumOfDoc());

}
    @Test
    public void checkDocsTestFiveFile(){
        ReadParseExecutor readParseExecutor = new ReadParseExecutor(5,"","");
        readParseExecutor.run("C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\fiveDocs");
        System.out.println(readParseExecutor.getNumOfDoc());

    }

    @Test
    public void checkDocsTest500File(){
        ReadParseExecutor readParseExecutor = new ReadParseExecutor(3,"C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\Engine\\PLEASEWORK","C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע");
        readParseExecutor.run("C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\400");
        System.out.println(readParseExecutor.getNumOfDoc());

    }

    @Test
    public void printPTRDict(){
        HashMap<String,Long> ptrdict = getptrdict("C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\DictionaryPTR.txt");
        for (Map.Entry<String,Long> entry: ptrdict.entrySet()
                ) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Test
    public void seekWordFromPos(){
        long pos = 495129;//greenprint
        try {
            RandomAccessFile raf = new RandomAccessFile("C:\\Users\\יובל בן אליעזר\\Desktop\\סמסטר ה\\אחזור מידע\\posting\\withoutStemming\\112merged.txt", "r");
            raf.seek(pos);
            System.out.println(raf.readLine());
            pos = 579530; //COLTER-AMERICAN
            raf.seek(pos);
            System.out.println(raf.readLine());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String,Long> getptrdict(String path) {

        HashMap<String,Long> object1 = null;

        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            object1 = (HashMap<String,Long>)in.readObject();

            in.close();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object1;

    }

    @Test
    public void checkDocsTestthiryeightFile(){
        ReadParseExecutor readParseExecutor = new ReadParseExecutor(5,"C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\Engine\\PLEASEWORK","C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע");
        readParseExecutor.run("C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\thriysomthing");
        System.out.println(readParseExecutor.getNumOfDoc());

    }


    @Test
    public void checkDocsTestALL(){
        ReadParseExecutor readParseExecutor = new ReadParseExecutor(3,"C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\Engine\\PLEASEWORK","C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע");
        readParseExecutor.run("C:\\Users\\יובל בן אליעזר\\Desktop\\אחזור מידע\\corpus");
        System.out.println(readParseExecutor.getNumOfDoc());

    }

//        System.out.println("posting Path:"+ indexer.getPostingPath());
//        System.out.println();
//        System.out.println();
//        System.out.println();
//        System.out.println("Dictionary:"+ indexer.getTermsPosting());

    //  Map<String,PostingLine> t = indexer.getTermsPosting();
    // Set r = t.entrySet();
//
//        System.out.println();
//        System.out.println();
//        System.out.println();
    //System.out.println("posting Dictionary:"+ indexer.getTermsPosting());

//        if(runWithParse)
//            System.out.println(res.toString());
}