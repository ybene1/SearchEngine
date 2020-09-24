package model;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
@XmlRootElement(name="top")
public class Query implements Serializable {


    private String queryID; //ID
    private Elements elements;
    private ArrayList<Term> listOfTerms; // The terms after parsing
    private ArrayList<Term> listOfEntity; //The list of words that may be entities
    private String queryString; // the string of the query
    private String description; // the description of the query


    public Query(String queryString) {
        this.queryString = queryString;
    }

    public Query(Element fileQuery) {
        this.elements = fileQuery.getAllElements();
        this.queryID = fileQuery.getElementsByTag("num").text().substring(8,11);
        this.queryString = fileQuery.getElementsByTag("title").text();
        this.listOfTerms = new ArrayList<Term>();
        this.listOfEntity = new ArrayList<Term>();
        this.description = fileQuery.getElementsByTag("desc").text();
        int index = this.description.indexOf("Narrative");
        this.description = this.description.substring(13,index);
        //this.queryString = this.queryString+" "+this.description;
        this.queryString = this.queryString+" "+this.queryString+" "+this.queryString+" "+this.queryString+" "+this.description;
    }

    public Query(String queryID, Elements elements, ArrayList<Term> listOfTerms, ArrayList<Term> listOfEntity, String queryString, String description, String narrative) {
        this.queryID = queryID;
        this.elements = elements;
        this.listOfTerms = listOfTerms;
        this.listOfEntity = listOfEntity;
        this.queryString = queryString;
        this.description = description;
    }
    public String getQueryString() {
        return queryString;
    }

    public void setListOfTerms(ArrayList<Term> listOfTerms) {
        this.listOfTerms = listOfTerms;
    }

    public void setListOfEntity(ArrayList<Term> listOfEntity) {
        this.listOfEntity = listOfEntity;
    }

    public ArrayList<Term> getListOfTerms() {
        return listOfTerms;
    }

    public String getQueryID() {
        return queryID;
    }

    public void setQueryID(String queryID) {
        this.queryID = queryID;
    }

    public void addStringToQuery(String queryString){
        this.queryString += " "+queryString;
    }

    public ArrayList<Term> getListOfEntity() {
        return listOfEntity;
    }
}
