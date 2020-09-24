package model ;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name="DOC")
public class Document implements Serializable {
    private String docNum; //ID
    private String text; // Text that has not passed parsing
    private Elements elements;
    private int sizeOfDoc; // length of the document
    private int maxTf; //The most common word frequency in a document
    private int uniqueTerms; //The number of words that appeared only once in the text
    private ArrayList<Term> listOfTerms; // The text after parsing
    private String title;
    private ArrayList<Term> listOfEntity; //The list of words that may be entities

    /**
     * Parameter Constructor
     * @param fileDoc element from jsoup --> getElementByTag(<DOCNO>)
     */
    public Document(Element fileDoc){
        this.elements = fileDoc.getAllElements();
        this.text = fileDoc.getElementsByTag("text").text();
        this.docNum = fileDoc.getElementsByTag("docno").text();
        this.sizeOfDoc = 0 ;
        this.listOfTerms = new ArrayList<Term>();
        this.listOfEntity = new ArrayList<Term>();
        this.uniqueTerms=0;
        this.maxTf=0;
        this.title="";
    }

    /**
     * Parameter Constructor
     * @param docNum Document name
     * @param text Text in the document
     * @param elements elements from jsoup --> getElementByTag(<DOCNO>)
     * @param sizeOfDoc Document length
     * @param maxTf The most frequent term in the document
     * @param uniqueTerms Numbers of unique terms
     * @param listOfTerms List of regular terms
     * @param listOfEntity  List of Entity
     * @param title The title of the document
     */
    public Document(String docNum, String text, Elements elements, int sizeOfDoc, int maxTf, int uniqueTerms, ArrayList<Term> listOfTerms,ArrayList<Term> listOfEntity, String title) {
        this.docNum = docNum;
        this.text = text;
        this.elements = elements;
        this.sizeOfDoc = sizeOfDoc;
        this.maxTf = maxTf;
        this.uniqueTerms = uniqueTerms;
        this.listOfTerms = listOfTerms;
        this.listOfEntity = listOfEntity;
        this.title = title;
    }

    /**
     * Getter to the name of the document
     * @return name of the document
     */
    public String getDocNum() {
        return docNum;
    }

    /**
     * Getter to the text in document
     * @return The text in document
     */
    public String getText() {
        return text;
    }

    /**
     * Getter to the elements
     * @return
     */
    public Elements getElements() {
        return elements;
    }

    /**
     *
     * @return the size go this document
     */
    public int getSizeOfDoc() {
        return sizeOfDoc;
    }

    /**
     *
     * @return getter for the maxTf
     */
    public int getMaxTf() {
        return maxTf;
    }

    /**
     *
     * @param maxTf setter for the maxTf
     */
    public void setMaxTf(int maxTf) {
        if(maxTf > this.maxTf){
            this.maxTf = maxTf;
        }
    }

    /**
     *
     * @return getter for the list of terms that represents entity
     */
    public ArrayList<Term> getListOfEntity() {
        return listOfEntity;
    }

    /**
     *
     * @param listOfEntity setter for the list of terms that represents entity
     */
    public void setListOfEntity(ArrayList<Term> listOfEntity) {
        this.listOfEntity = listOfEntity;
    }


    /**
     * getter for the number of the unique terms
     * @return the number of the unique terms
     */
    public int getUniqueTerms() {
        return uniqueTerms;
    }

    /**
     * setter for the number of the unique terms
     * @param uniqueTerms the number of the unique terms
     */
    public void setUniqueTerms(int uniqueTerms) {
        this.uniqueTerms = uniqueTerms;
    }

    /**
     * getter for the list of terms of this document
     * @return list of terms of this document
     */
    public ArrayList<Term> getListOfTerms() {
        return listOfTerms;
    }

    /**
     * setter for the list of terms of this document
     * @param listOfTerms list of terms of this document
     */
    public void setListOfTerms(ArrayList<Term> listOfTerms) {
        this.listOfTerms = listOfTerms;
    }

    /**
     *
     * @return the title of the document
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for the size of the document
     * @param sizeOfDoc the size of the document
     */
    public void setSizeOfDoc(int sizeOfDoc) {
        this.sizeOfDoc = sizeOfDoc;
    }

    /**
     * setter for the docno feild
     * @param DOCNO
     */
    @XmlElement
    public void setDOCNO(String DOCNO) {
        this.docNum = DOCNO;
    }

    /**
     * setter for the text field
     * @param TEXT
     */
    @XmlElement
    public void setTEXT(String TEXT) {
        this.text = TEXT;
    }

    /**
     * getter for the docno of this document
     * @return the docno of this document
     */
    public String getDOCNO() {
        return this.docNum;
    }

    /**
     * getter for the Text in this document
     * @return the Text in this document
     */
    public String getTEXT() {
        return this.text;
    }
    public Document() {
    }

    @Override
    public String toString() {
        return "Document{" +
                "DOCNO='" + this.docNum + '\'' +
                ", TEXT='" + this.listOfTerms.toString() + '\'' +
                "SIZE='" + this.sizeOfDoc + '\'' +
                ", MAX_TF='" + this.maxTf + '\'' +
                "UNIQUE_TERMS='" + this.uniqueTerms + '\'' +
                '}';
    }
}
