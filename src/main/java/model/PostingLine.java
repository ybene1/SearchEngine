package model;
import java.io.Serializable;
import java.util.ArrayList;

//The line as it is written to the disk of the posting file
public class PostingLine implements Serializable {
    private String nameOfTerm;
    private TermType termType;
    private ArrayList<PostingRecord> postingRecordArrayList;

    /**
     * the construction of PostingLine
     * @param nameOfTerm
     * @param postingRecordArrayList
     * @param termType
     */
    public PostingLine(String nameOfTerm, ArrayList<PostingRecord> postingRecordArrayList,TermType termType) {
        this.nameOfTerm = nameOfTerm;
        this.postingRecordArrayList = postingRecordArrayList;
        this.termType = termType;
    }

    /**
     * @return the type of the trem
     */
    public TermType getTermType() {
        return termType;
    }

    /**
     * setter for the term type
     * @param termType
     */
    public void setTermType(TermType termType) {
        this.termType = termType;
    }

    /**
     * getter for the name of the term
     * @return name of the term
     */
    public String getNameOfTerm() {
        return nameOfTerm;
    }

    /**
     * getter for the posting record array list
     * @return the posting record array list
     */
    public ArrayList<PostingRecord> getPostingRecordArrayList() {
        return postingRecordArrayList;
    }

    /**
     * setter for the posting record array list
     * @param postingRecordArrayList
     */
    public void setPostingRecordArrayList(ArrayList<PostingRecord> postingRecordArrayList) {
        this.postingRecordArrayList = postingRecordArrayList;
    }

    /**
     * merges the posting line to the posting record list
     * @param PostingLine
     */
    public void mergePostingLine(PostingLine PostingLine){
        this.postingRecordArrayList.addAll(PostingLine.postingRecordArrayList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nameOfTerm + "&");
        for (int i = 0; i < postingRecordArrayList.size(); i++) {
            PostingRecord p = postingRecordArrayList.get(i);
            stringBuilder.append(p.getDocId());
            stringBuilder.append(",");
            stringBuilder.append(p.getTf());
            stringBuilder.append(",");
            stringBuilder.append(p.termType());
            stringBuilder.append("|");
        }

        return stringBuilder.toString();
    }
}