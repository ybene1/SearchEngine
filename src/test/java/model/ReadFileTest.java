package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReadFileTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void main() {
    }

    @Test
    public void listFilesForFolder() {
    }

    @Test
    public void readFile() {
//        ReadFile readFile = new ReadFile("C:\\Users\\Tair\\Desktop\\corpus");
//        readFile.runReadFiles();
//        System.out.println(readFile.getNumOfDocs());
    }

    @Test
    public void threadReadFileRun() {
        ReadParseExecutor readParseExecutor = new ReadParseExecutor(5,"C:\\Users\\Tair\\Desktop\\engine\\t","C:\\Users\\Tair\\Desktop\\engine\\Engine");
        readParseExecutor.run("C:\\Users\\Tair\\Desktop\\engine\\Engine");
        System.out.println(readParseExecutor.getNumOfDoc());
    }

    @Test
    public void jsoupDoc() {
    }

    @Test
    public void printDoc() {
    }
}