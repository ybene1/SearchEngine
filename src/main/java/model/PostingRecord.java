package model;
import java.io.Serializable;

/**
 * this class represent posting record in the posting line
 */
public class PostingRecord implements Serializable {
    private String docId;
    private int tf; // Total frequency
    private TermType termType; // If the term is in title

    /**
     * the constructor of Posting Record
     * @param docId
     * @param tf
     * @param termType
     */
    public PostingRecord(String docId, int tf, TermType termType) {
        this.docId = docId;
        this.tf = tf;
        this.termType = termType;
    }

    /**
     * @return this doc id
     */
    public String getDocId() {
        return docId;
    }

    /**
     * setter for this doc id
     * @param docId
     */
    public void setDocId(String docId) {
        this.docId = docId;
    }

    /**
     * getter for the Tf
     * @return the tf number
     */
    public int getTf() {
        return tf;
    }

    /**
     * setter for the Tf
     * @param tf
     */
    public void setTf(int tf) {
        this.tf = tf;
    }

    /**
     * getter for this term type
     * @return the term type
     */
    public TermType termType() {
        return termType;
    }

    /**
     * setter for this term type
     * @param termType
     */
    public void setInTermType(TermType termType) {
        this.termType = termType;
    }

    @Override
    public String toString() {
        return "PostingRecord{" +
                "docId='" + docId + '\'' +
                ", tf=" + tf +
                ", TermType=" + termType +
                '}';
    }
}