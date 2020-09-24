package model;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MergerTest {

    @Test
    public void setUp() throws Exception {

        PrintWriter writer = new PrintWriter("C:\\Users\\Tair\\Desktop\\Tair - BGU\\test\\text.txt");
        writer.print("");
        writer.close();
    }

    @Test
    public void listFilesForFolder() {



        //        ExecutorService executor = Executors.newFixedThreadPool(7);
////        String destPath = "C:\\Users\\Tair\\Desktop\\engine\\t";
////        Runnable [] runnableMergerArray = new Merger[25];
////        Runnable [] runnableMergerArray1 = new Merger[26];
////        Runnable runnableMerger7 = new Merger();
////        int index = 0;
////        for (char c = 'b'; c <= 'z'; c++) {
////            runnableMergerArray[index] = new Merger();
////            ((Merger)runnableMergerArray[index]).setDestPath(destPath+"\\smallLetters\\"+c);
////            executor.execute(runnableMergerArray[index]);
////            index++;
////        }
////        int index1 = 0;
////        for (char c = 'A'; c <= 'Z'; c++) {
////            runnableMergerArray1[index1] = new Merger();
////            ((Merger)runnableMergerArray1[index1]).setDestPath(destPath+"\\bigLetters\\"+c);
////            executor.execute(runnableMergerArray1[index1]);
////            index1++;
////        }
////        ((Merger) runnableMerger7).setDestPath( (destPath+"\\range"));
////        executor.execute(runnableMerger7);
////        ((Merger) runnableMerger7).setDestPath( (destPath+"\\prices"));
////        executor.execute(runnableMerger7);
////        ((Merger) runnableMerger7).setDestPath( (destPath+"\\dates"));
////        executor.execute(runnableMerger7);
////        ((Merger) runnableMerger7).setDestPath( (destPath+"\\numbers"));
////        executor.execute(runnableMerger7);
////        ((Merger) runnableMerger7).setDestPath( (destPath+"\\percents"));
////        executor.execute(runnableMerger7);
////        executor.shutdown();
////        while (!executor.isTerminated()) {
        //     }
    }


    @Test
    public void entityMerge() {
        Merger merger = new Merger("d:\\documents\\users\\tairc\\Documents\\engine\\posting\\entity");
        Indexer indexer = new Indexer();
        File file = new File("d:\\documents\\users\\tairc\\Documents\\engine\\posting\\entity\\test.txt");
     //   merger.entityFinal(file);
        BufferedReader reader;

        File newFile = new File(file.getParent() + "\\entity.txt");
        int counter = 0;
        try {
            reader = new BufferedReader(new FileReader(newFile));
            String line = reader.readLine();
            while (line != null) {
                counter++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(counter);

    }


    public List<Pair<String,Integer>> parseToPostingLine(String postingLineToParse){
        int index = postingLineToParse.indexOf("&");
        postingLineToParse=postingLineToParse.substring(index+1);
        String[] arrayOfLine = postingLineToParse.split("[|]");
        List<Pair<String,Integer>> recordArrayList = new ArrayList<>();
        for (String s:
                arrayOfLine) {
            String[] parseRecord = s.split(",");
            Pair<String,Integer> pair = new Pair<>(parseRecord[0],Integer.parseInt(parseRecord[1]));
            recordArrayList.add(pair);
        }
        return recordArrayList;

    }

    @Test
    public void parseToPostingLine() {
        String postingEntityLine = "ADDITIONALLY MARKOV&FBIS3-72,1,entity|FBIS3-74,1,entity|";
        List<Pair<String,Integer>> recordArrayList = new ArrayList<>();
        recordArrayList = parseToPostingLine(postingEntityLine);
        String postingLine2 = "BIASED UNINFORMATIVE RUSSIA LAST MINUTE STATE TV CAMPAIGN AGAINST ZHIRINOVSKIY RUSSIA PRO&FBIS3-72,1,entity|FBIS3-74,5,entity|";
        List<Pair<String,Integer>> recordArrayList2 = new ArrayList<>();
        recordArrayList2 = parseToPostingLine(postingLine2);



    }

    private boolean checkIfNeedToAddToListAndRemoveSmallestIfYes(List<Pair<String,Integer>> list, String entity, Integer num) {
        int min = Integer.MAX_VALUE;
        Pair<String,Integer> least = null;
        for (Pair<String, Integer> pair :
                list) {
            if(pair.getValue()<min){
                min = pair.getValue();
                least=pair;
            }
        }
        if(num>min){
            list.remove(least);
            return true;
        }
        return false;

    }

    @Test
    public void checkIfNeedToAddToListAndRemoveSmallestIfYes() {
        //Less then 5 entity in a doc
        List<Pair<String,Integer>> list = new LinkedList<>();
        list.add(new Pair<>("Yuval",5));
        list.add(new Pair<>("Yuval1",3));
        list.add(new Pair<>("yuval3",4));
        list.add(new Pair<>("yuval5",2));
        //False - it good
        System.out.println(checkIfNeedToAddToListAndRemoveSmallestIfYes(list,"yuval10",1));
        list.add(new Pair<>("yuval10",10));
        System.out.println(checkIfNeedToAddToListAndRemoveSmallestIfYes(list,"yuval90",10));
        System.out.println(list);


    }
}
