package model;

import java.io.Serializable;

public class Term implements Serializable {

    private String nameAfter; //To save only name after
    private TermType termType;
    private int df; //The amount of documents in which the word appears
    //if the word has already appeared in the dictionary in the indexer ,
    //And we want to put it in again, so we just update this var and totalTf.
    private int totalTf; // The number of times the word appeared in the document ,
    // at first in the parser we are save here the number of appearance in the text,
    // and in the indexer we are calculate the total_tf if the word has already appeared in the dictionary,
    //And we want to put it in again, so we just update this var and df.

    /**
     * the constructor of this class
     * @param termType
     * @param nameAfter
     */
    public Term(TermType termType, String nameAfter) {
        this.nameAfter = nameAfter;
        this.termType = termType;
        this.termType = termType;
        this.df = 1;
        this.totalTf = 1;
    }

    /**
     * The function checks whether the name of the word after parsing is the same as the word received
     * @param nameAfter
     * @return true if the name after parsing is equal to the name is given
     */
    public boolean equalsByName(String nameAfter){
        if (nameAfter != null) {
            if(nameAfter.equals(this.nameAfter)){
                return true;
            }
        }
        return false;
    }

    /**
     * getter for the name after parse
     * @return the name after parse
     */
    public String getNameAfter() {
        return nameAfter;
    }

    /**
     * getter for the term type by the enum we defined
     * @return the term type of this term
     */
    public TermType getTermType() {
        return termType;
    }

    /**
     * getter for the df
     * @return the df
     */
    public int getDf() {
        return df;
    }

    /**
     * setter for the df
     * @param df
     */
    public void setDf(int df) {
        this.df += df;
    }

    /**
     * setter for the totalTf
     * @param totalTf
     */
    public void setTotaTf(int totalTf) {
        this.totalTf += totalTf;
    }

    /**
     * getter for the total tf
     * @return
     */
    public int getTotalTf() {
        return totalTf;
    }


    /**
     * setter for the name after parse
     * @param nameAfter
     */
    public void setNameAfter(String nameAfter) {
        this.nameAfter = nameAfter;
    }

    @Override
    public String toString() {
        return
                "nameAfter = " + nameAfter +"|"+
                        " total Tf = " + totalTf +"|"+
                        " df = " + df +"|";
    }
}
