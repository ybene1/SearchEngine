package model;

import java.io.Serializable;

public class DocumentInfo implements Serializable {
    private String docNum; //ID
    private int sizeOfDoc; // length of the document
    private int maxTf; //The most common word frequency in a document
    private int uniqueTerms; // the number of the unique terms in this document

    /**
     * constructor for DocumentInfo
     *
     * @param fileDoc
     */
    public DocumentInfo(Document fileDoc) {
        this.docNum = fileDoc.getDOCNO();
        this.sizeOfDoc = fileDoc.getSizeOfDoc();
        this.uniqueTerms = fileDoc.getUniqueTerms();
        this.maxTf = fileDoc.getMaxTf();
    }

    /**
     * setter for the maxTf
     * @param maxTf
     */
    public void setMaxTf(int maxTf) {
        this.maxTf = maxTf;
    }


    /**
     * setter for the number of the unique terms
     * @param uniqueTerms
     */
    public void setUniqueTerms(int uniqueTerms) {
        this.uniqueTerms = uniqueTerms;
    }

    /**
     * getter for the document number
     * @return the document number
     */
    public String getDocNum() {
        return docNum;
    }

    /**
     * getter for the size of the document
     * @return the size of the document
     */
    public int getSizeOfDoc() {
        return sizeOfDoc;
    }


    /**
     * getter for the maxTf
     * @return the number of the maxTf
     */
    public int getMaxTf() {
        return maxTf;
    }

    /**
     * getter for the number of the unique terms
     * @return the number of the unique terms
     */
    public int getUniqueTerms() {
        return uniqueTerms;
    }

    @Override
    public String toString() {
        return "DocumentInfo{" +
                "DOCNO='" + this.docNum + '\'' +
                "SIZE='" + this.sizeOfDoc + '\'' +
                ", MAX_TF='" + this.maxTf + '\'' +
                "UNIQUE_TERMS='" + this.uniqueTerms + '\'' +
                '}';
    }


}
