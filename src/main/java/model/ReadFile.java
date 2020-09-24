package model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile implements Runnable{

    private int numOfDocs;
    private List<File> filesToRead;
    private boolean runWithParse = true;
    private ReadParseExecutor readParseExecutor;
    private boolean withStemming;
    protected Indexer indexer;
    private String destPath;
    private String srcPath;


    /**
     * constructor of ReadFile
     * @param filesToReadPath
     */
    public ReadFile(List<File> filesToReadPath) {
        this.filesToRead = filesToReadPath;
        indexer = new Indexer();

    }

    /**
     * constructor of ReadFile
     * @param filesToRead
     * @param runWithParse
     * @param readParseExecutor
     * @param withStemming
     * @param destPath
     * @param srcPath
     */
    public ReadFile(List<File> filesToRead, boolean runWithParse, ReadParseExecutor readParseExecutor,
                    boolean withStemming,String destPath,String srcPath) {
        this(filesToRead);
        this.runWithParse=runWithParse;
        this.readParseExecutor = readParseExecutor;
        this.withStemming = withStemming;
        this.destPath = destPath;
        this.srcPath = srcPath;
    }

    /**
     * this function reads all the files in the destPath and create documents,
     * after we sends it to the parser and the indexer.
     * @throws IOException
     */
    private void jsoupDoc() throws IOException {
        ArrayList<Document> documentList = new ArrayList<>();
        for (int i = 0; i < filesToRead.size(); i++) {
            File input = filesToRead.get(i);
            org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8", "");
            Elements docs = doc.getElementsByTag("DOC");
            for (Element d :
                    docs) {
                Document document = new model.Document(d);
                documentList.add(document);
            }
        }
        Parse parser = new Parse(srcPath+"\\stop_words.txt");
        for (Document document :
                documentList) {
            numOfDocs++;
            if(runWithParse) {
                parser.parseText(document,this.withStemming);
            }
        }
        if(runWithParse) {
            Indexer indexer = new Indexer();
            indexer.setPostingPath(destPath);
            indexer.createInvertedIndex(documentList);
        }
    }

    /**
     * this is the main function of this class that starts the jsoup - we
     * read the corpus folder with all the files
     */
    public void run() {
        try {
            this.numOfDocs = 0;
            try {
                jsoupDoc();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            this.readParseExecutor.addToNumOFDocs(this.numOfDocs);
        }
        catch (Exception e){
            e.printStackTrace();
            this.readParseExecutor.killAllAndExit();
        }
    }
}


