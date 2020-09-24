package model;
import java.io.Serializable;

/**
 This dictionary represents the temporary dictionary whose data is saved to disk
 */
public class DictionaryEntry implements Serializable {

    private int totalDocuments; //df
    private int totalTf; //tf
    private TermType termType; //Type of the term

    /**
     *Parameter Constructors
     * @param totalDocuments df
     * @param totalTf tf
     * @param t term type
     */
    public DictionaryEntry(int totalDocuments, int totalTf, TermType t) {
        this.totalDocuments = totalDocuments;
        this.totalTf = totalTf;
        this.termType = t;
    }

    /**
     * Adding one more Document to df
     */
    public void addOneMoreDocument(){
        this.totalDocuments++;
    }

    /**
     * Computing the total tf in the BATCH of documents
     * @param tf tf
     */
    public void updateTf(int tf){
        this.totalTf += tf;
    }

    /**
     * Getter to df
     * @return df
     */
    public int getTotalDocuments() {
        return totalDocuments;
    }

    /**
     * Setter to df
     * @param totalDocuments df to set
     */
    public void setTotalDocuments(int totalDocuments) {
        this.totalDocuments = totalDocuments;
    }

    /**
     * Getter to tf
     * @return tf
     */
    public int getTotalTf() {
        return totalTf;
    }

    /**
     * Setter to tf
     * @param totalTf tf to set
     */
    public void setTotalTf(int totalTf) {
        this.totalTf = totalTf;
    }

    /**
     * Getter to Term Type
     * @return Term Type
     */
    public TermType getTermType() {
        return termType;
    }

    /**
     * Setter to Term Type
     * @param termType Term Type to set
     */
    public void setTermType(TermType termType) {
        this.termType = termType;
    }

    @Override
    public String toString() {
        return "DictionaryEntry{" +
                "totalDocuments=" + totalDocuments +
                ", totalTf=" + totalTf +
                ", termType=" + termType +
                '}';
    }
}
