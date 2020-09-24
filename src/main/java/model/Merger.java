package model;

import javafx.util.Pair;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Merger implements Runnable {


    private Queue<File> fileQueue;
    private String destPath;
    private AtomicInteger index;
    public final static String FINAL_MERGED_FILE_NAME = "Final_merged.txt";

    /**
     * constructor for this class
     * @param destPath
     */
    public Merger(String destPath) {
        this.index = new AtomicInteger(0);
        this.destPath = destPath;
        this.fileQueue = new LinkedList<>();
        listFilesForFolder(new File(destPath));
    }

    /**
     * fill the queue with filed that need to be merge
     * @param folder
     */
    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                // listFilesForFolder(fileEntry);
                continue;
            } else {
                fileQueue.add(fileEntry);
            }
        }
    }

    /**
     * merge all the files in the queue
     */
    public void mergeQueue() {
        while (fileQueue.size() > 2) {
                mergeTwoFiles(fileQueue.poll(), fileQueue.poll());
        }
        if (fileQueue.size()==2){
            mergeLastTwoFiles(fileQueue.poll(),fileQueue.poll());
        }
        else if (fileQueue.size()==1){
            File empty = new File(getTempFileName());
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(empty));
                writer.write("");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mergeLastTwoFiles(fileQueue.poll(),empty);
        }
       // createAndSavePTRDict(fileQueue.poll());
    }

    private void mergeLastTwoFiles(File f1, File f2) {
        BufferedReader reader1;
        BufferedReader reader2;
        HashMap<String, Long> toSave = new HashMap<>();
        long pos = 0;
        // StringBuilder writeAll = new StringBuilder();
        try {
            RandomAccessFile raf = new RandomAccessFile(this.destPath + '\\' +"merged.txt","rw");
            BufferedWriter writer = new BufferedWriter(new FileWriter(raf.getFD()));
            reader1 = new BufferedReader(new FileReader(
                    f1.getAbsolutePath()));
            reader2 = new BufferedReader(new FileReader(
                    f2.getAbsolutePath()));
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            int index1, index2;
            while (line1 != null && line2 != null) {
                index1 = line1.indexOf("&");
                index2 = line2.indexOf("&");
                if (index1 == -1 || index2 == -1) {
                    continue;
                }
                // l1 and l2 represent only the term itself, without the "&" and the rest exp
                String l1 = line1.substring(0, index1);
                String l2 = line2.substring(0, index2);
                String wordToSave;
                if (l1.compareTo(l2) < 0) {
//                    writeAll.append(line1+"\n");
                    writer.write(line1 + "\n");
                    wordToSave = l1;
                    line1 = reader1.readLine();

                } else if (l1.compareTo(l2) > 0) {
//                    writeAll.append(line2+"\n");
                    writer.write(line2 + "\n");
                    wordToSave = l2;
                    line2 = reader2.readLine();

                } else {
                    String toWrite = line2.substring(index2 + 1);
//                    writeAll.append(line1);
                    writer.write(line1);
//                    writeAll.append(toWrite+"\n");
                    writer.write(toWrite + "\n");
                    wordToSave=l1;
                    line1 = reader1.readLine();
                    line2 = reader2.readLine();
                }
                writer.flush();
                toSave.put(wordToSave,pos);
                long fileOffset = raf.getFilePointer();
                pos = fileOffset;
            }
            while (line1 != null) {
//                writeAll.append(line1+"\n");
                writer.write(line1 + "\n");
                String wordToSave = line1.substring(0,line1.indexOf("&"));
                writer.flush();
                toSave.put(wordToSave,pos);
                pos = raf.getFilePointer();
                line1 = reader1.readLine();

            }
            while (line2 != null) {
//                writeAll.append(line2+"\n");
                writer.write(line2 + '\n');
                String wordToSave = line2.substring(0,line2.indexOf("&"));
                writer.flush();
                toSave.put(wordToSave,pos);
                pos = raf.getFilePointer();
                line2 = reader2.readLine();

            }
            reader1.close();
            reader2.close();
            writer.flush();
            writer.close();
            serializeToDisk(toSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        f1.delete();
        f2.delete();
    }

    /**
     * merge without writing ptr files
     */
    public void mergeQueueWithoutPtr() {
        while (fileQueue.size() >= 2) {
            mergeTwoFiles(fileQueue.poll(), fileQueue.poll());
        }
    }

    /**
     * save ptr dictionary in a file
     * @param merged
     */
    public void createAndSavePTRDict(File merged) {
        HashMap<String, Long> toSave = new HashMap<>();
        try {
            RandomAccessFile raf = new RandomAccessFile(merged, "r");
            BufferedReader brRafReader = new BufferedReader(
                    new FileReader(raf.getFD()));
            String line = null;
            long currentOffset = 0;
            long previousFileOffset = -1;
            long nextLineRealOffset = 0;
            while ((line = brRafReader.readLine()) != null) {
                long fileOffset = raf.getFilePointer();
                if (fileOffset != previousFileOffset) {
                    if (previousFileOffset != -1) {
                        currentOffset = previousFileOffset;
                    }
                    previousFileOffset = fileOffset;
                }
                long bufferOffset = getOffset(brRafReader);
                toSave.put(getTermFromLine(line), nextLineRealOffset);
                // add at PartB - the dictionary ptr assignment
                if(merged.getName().contains("entity")){
                    Indexer.entityDictionaryWithPtr.put(getTermFromLine(line), nextLineRealOffset);
                }
                else {
                    Indexer.dictionaryWithPtr.put(getTermFromLine(line), nextLineRealOffset);
                }
                nextLineRealOffset = currentOffset + bufferOffset;
            }
            serializeToDisk(toSave);
            brRafReader.close();
            raf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getOffset(BufferedReader bufferedReader) throws Exception {
        Field field = BufferedReader.class.getDeclaredField("nextChar");
        long result = 0;
        try {
            field.setAccessible(true);
            result = (long)((Integer) field.get(bufferedReader));
        } finally {
            field.setAccessible(false);
        }
        return (long) result;
    }

    /**
     * saving the hash map to disk
     * @param toSave
     */
    private void serializeToDisk(HashMap<String, Long> toSave) {
        StringBuilder t = new StringBuilder();
        t.append(destPath);
        t.append("\\DictionaryPTR.txt");
        try {
            FileOutputStream fos = new FileOutputStream(t.toString());
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(toSave);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTermFromLine(String line) {
        int index = line.indexOf('&');
        return line.substring(0, index);
    }

    /**
     * merge two files into f1 - the f1 will be sorted after this merge
     * @param f1
     * @param f2
     */
    private void mergeTwoFiles(File f1, File f2) {
        BufferedReader reader1;
        BufferedReader reader2;
        File newFile = new File(getTempFileName());
        // StringBuilder writeAll = new StringBuilder();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));
            reader1 = new BufferedReader(new FileReader(
                    f1.getAbsolutePath()));
            reader2 = new BufferedReader(new FileReader(
                    f2.getAbsolutePath()));
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            int index1, index2;
            while (line1 != null && line2 != null) {
                index1 = line1.indexOf("&");
                index2 = line2.indexOf("&");
                if (index1 == -1 || index2 == -1) {
                    continue;
                }
                // l1 and l2 represent only the term itself, without the "&" and the rest exp
                String l1 = line1.substring(0, index1);
                String l2 = line2.substring(0, index2);
                if (l1.compareTo(l2) < 0) {
//                    writeAll.append(line1+"\n");
                    writer.write(line1 + "\n");
                    line1 = reader1.readLine();
                    continue;
                } else if (l1.compareTo(l2) > 0) {
//                    writeAll.append(line2+"\n");
                    writer.write(line2 + "\n");
                    line2 = reader2.readLine();
                    continue;
                } else {
                    String toWrite = line2.substring(index2 + 1);
//                    writeAll.append(line1);
                    writer.write(line1);
//                    writeAll.append(toWrite+"\n");
                    writer.write(toWrite + "\n");
                    line1 = reader1.readLine();
                    line2 = reader2.readLine();

                }
            }
            while (line1 != null) {
//                writeAll.append(line1+"\n");
                writer.write(line1 + "\n");
                line1 = reader1.readLine();
            }
            while (line2 != null) {
//                writeAll.append(line2+"\n");
                writer.write(line2 + '\n');
                line2 = reader2.readLine();
            }
            reader1.close();
            reader2.close();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        f1.delete();
        f2.delete();
        this.fileQueue.add(newFile);
//        try {
//            // cleaning the content of the file
//            PrintWriter printWriter = new PrintWriter(f1.getAbsolutePath());
//            printWriter.print("");
//            printWriter.close();
//            // writing all the sorted file into f1
//            writer = new BufferedWriter(new FileWriter(f1.getAbsolutePath()));
//            writer.write(writeAll.toString());
//            writer.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private String getTempFileName() {
        return this.destPath + '\\' + index.getAndIncrement() + "merged.txt";
    }

    @Override
    public void run() {
//        mergeQueue();
    }


    /**
     * this function gets final posting file and writing new posting file with only entities that
     * appeared in more then one document, depends on the size of the list of the documents in the posting line.
     *
     * @param  path path of the file
     */
    public void entityFinal(String path) {
        if(path == null){
            return;
        }
        HashMap<String, Long> toSave = new HashMap<>();
        BufferedReader reader;
        File fileToMerge = new File(fileQueue.poll().getAbsolutePath());
        long pos =0;
        try {
            RandomAccessFile raf = new RandomAccessFile(path + "\\entity.txt","rw");
            BufferedWriter writer = new BufferedWriter(new FileWriter(raf.getFD()));
            reader = new BufferedReader(new FileReader(fileToMerge));
            String line = reader.readLine();
            String[] arrayOfLine;
            while (line != null) {
                int index = line.indexOf("&");
                if (index == -1) {
                    continue;
                }
                // term represent only the term itself, without the "&" and the rest exp
                String theTerm = line.substring(0, index);
                arrayOfLine = line.split("[|]");
                // you are an entity because we saw you in more then one document
                if (arrayOfLine.length > 1) {
                    toSave.put(theTerm,pos);
                    writer.write(line + "\n");
                    writer.flush();
                    pos = raf.getFilePointer();
                    Indexer.dictionaryDF.put(theTerm,new AtomicInteger(arrayOfLine.length));
               //     Indexer.entityDictionaryWithPtr.put(theTerm,new Long(0));
                    int totalTf = 0;
                        for (String s: arrayOfLine) {
                        String [] temp = s.split(",");
                        totalTf += Integer.parseInt(temp[1]);
                    }
                    Indexer.dictionaryTotal.put(theTerm,new AtomicInteger(totalTf));
                    Indexer.numberOfTerms.addAndGet(1);
                }
                line = reader.readLine();
            }
            reader.close();
            writer.flush();
            writer.close();
            raf.close();
            fileToMerge.delete();
            serializeToDisk(toSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * The function creates a dictionary of document
     * number and the five entities that appear in this document most often
     * and then, save this dictionary to the disk
     * @param path path to the posting files
     */
    public void invertedDocAndEntity(String path) {
        HashMap<String, List<Pair<String, Integer>>> invertedDocEntity = new HashMap<>();
        File newFile = new File(path + "\\entity.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(newFile));
            String line;
            while ((line = reader.readLine()) != null) {
                List<Pair<String, Integer>> docAndNumList = getEntityPropertiesFromLine(line);
                String entity = getEntityFromLine(line);
                for (Pair<String, Integer> docAndNum :
                        docAndNumList) {
                    String docno = docAndNum.getKey();
                    Integer num = docAndNum.getValue();
                    if (invertedDocEntity.containsKey(docno)) {
                        List<Pair<String, Integer>> list = invertedDocEntity.get(docno);
                        if (list.size() < 5) {
                            list.add(new Pair<>(entity, num));
                        } else {
                            if (checkIfNeedToAddToListAndRemoveSmallestIfYes(list, entity, num)) {
                                list.add(new Pair<>(entity, num));
                            }
                        }
                    } else {
                        List<Pair<String, Integer>> list = new LinkedList<>();
                        list.add(new Pair<>(entity, num));
                        invertedDocEntity.put(docno, list);
                    }
                }
            }
          //  reader.close();
            //Send For Saving Dictionary
            serializeMapOfInvertedDocEntityToDisk(invertedDocEntity);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Save the final dictionary of doc-5 more common entities to the disk
     * @param toSave the dictionary
     */
    private void serializeMapOfInvertedDocEntityToDisk(HashMap<String, List<Pair<String, Integer>>>  toSave) {
        StringBuilder t = new StringBuilder();
        t.append(destPath);
        t.append("\\DictionaryDocEntity.txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(t.toString());
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(toSave);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param line
     * @return
     */
    private List<Pair<String,Integer>> getEntityPropertiesFromLine(String line) {
        return parseToPostingLine(line);
    }


    /**
     *Extract from the posting line the list of documents in which the word
     * appeared and the number of times the word appeared
     * @param postingLineToParse posting line
     * @return list of the docs and the tf of this term
     */
    private List<Pair<String,Integer>> parseToPostingLine(String postingLineToParse){
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

    /**
     * Extract the entity name from a posting row
     * @param line line in the posting file
     * @return the name of the term
     */
    private String getEntityFromLine(String line) {
        int index = line.indexOf("&");
        return line.substring(0,index);
    }

    /**
     *If a word appears in a document that appears less than the word received in the input
     * then the function removes the word from the list of documents and returns false.
     * Otherwise returns true
     * @param list List of 5 most common entities that appear in the document
     *             and the number of times they appeared in it
     * @param entity name of entity
     * @param num tf
     */
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

}