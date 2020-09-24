package model;

import com.medallia.word2vec.Word2VecModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Engine {
    private Searcher searcher;
    private boolean semantic;
    private boolean searchEntity;
    private String queryPath;
    private String queryString;
    private Parse parse;
    private String destPath;


    /**
     * this function parses the multiply queries, and sends the list of terms to the searcher
     * search function for each query, and get from it their list of relevant documents numbers
     * @param stemming
     * @return hash map of queryID, and their list of relevant documents numbers sorted by their rank
     */
    public HashMap<String, ArrayList<String>> runSearchFromQueriesFile(Boolean stemming) {
        callParserConstructor(stemming);
        renameDestPath(stemming);
        this.searcher = new Searcher(semantic, searchEntity, destPath);
        // open the file with the query path and create queries instances
        // the hash map holds for each query, the list of relevant docs that the searcher found
        HashMap<String, ArrayList<String>> queryArrayListDictionary = new HashMap<>();
        List<Query> queryList = openFile(new File(queryPath));
        ArrayList<String> queryIdArrayList = new ArrayList<>();
        // sending the query to the parser and then to the searcher
        for (Query q : queryList) {
            queryIdArrayList.add(q.getQueryID());
            addSemanticToQuery(q);
            parse.parseQuery(q, stemming);
            ArrayList<Term> listForSearcher = q.getListOfTerms();
            listForSearcher.addAll(q.getListOfEntity());
            queryArrayListDictionary.put(q.getQueryID(), searcher.search(listForSearcher, destPath + "\\entity", destPath));
        }
        saveTrecEvalMultiplyQueries(queryIdArrayList, queryArrayListDictionary);
        return queryArrayListDictionary;
    }

    /**
     * this function save the results into a result file for trec eval software
     * @param queryList
     * @param queryArrayListDictionary
     */
    private void saveTrecEvalMultiplyQueries(ArrayList<String> queryList, HashMap<String, ArrayList<String>> queryArrayListDictionary) {
        Collections.sort(queryList);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(destPath + "\\results.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int counter = 0;
        for (String queryId : queryList) {
            counter++;
            ArrayList<String> strings = queryArrayListDictionary.get(queryId);
            for (int i = 0; i < strings.size(); i++) {
                String toWrite = queryId + " 1 " + strings.get(i) + " 1 43 mt\n";
                try {
                    // TODO: 1/11/2020 chagne the path to the save path - copy the file to there
                    writer.write(toWrite);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (i == strings.size() - 1 && counter == queryList.size()) {
                    try {
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    /**
     * this function renames the dest path depending on the stemming check box
     * @param stemming
     */
    private void renameDestPath(Boolean stemming) {
        if (stemming) {
            destPath += "\\stemming";
        } else {
            destPath += "\\withoutStemming";
        }
    }

    /**
     * this function parses the single query, and sends the list of terms to the searcher
     * search function, and get from it their list of relevant documents numbers sorted
     * @param stemming
     * @return list of relevant documents numbers
     */
    public ArrayList<String> runSearchSingleQuery(Boolean stemming) {
        callParserConstructor(stemming);
        renameDestPath(stemming);
        this.searcher = new Searcher(semantic, searchEntity, destPath);
        Query query = new Query(queryString);
        // 111 is the default query id for single query
        query.setQueryID("111");
        addSemanticToQuery(query);
        parse.parseQuery(query, stemming);
        ArrayList<Term> listForSearcher = query.getListOfTerms();
        listForSearcher.addAll(query.getListOfEntity());
        ArrayList<String> result = searcher.search(listForSearcher, destPath + "\\entity", destPath);
        saveTrecEvalSingleQuery(query.getQueryID(), result);
        return result;
    }


    /**
     * this function save the results into a result file for trec eval software
     * @param queryId
     * @param strings
     */
    private void saveTrecEvalSingleQuery(String queryId, ArrayList<String> strings) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(destPath + "\\results.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < strings.size(); i++) {
            String toWrite = queryId + " 1 " + strings.get(i) + " 1 43 mt\n";
            try {
                writer.write(toWrite);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (i == strings.size() - 1) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * this function calls the parser constructor depending on the stemming check box
     * @param stemming
     */
    private void callParserConstructor(boolean stemming) {
        if (stemming) {
            this.parse = new Parse(destPath + "\\stemming\\stop_words.txt");
        } else {
            this.parse = new Parse(destPath + "\\withoutStemming\\stop_words.txt");
        }
    }

    /**
     * this function open the file of the query and creating the query
     * @param file
     * @return
     */
    public List<Query> openFile(File file) {
        List<Query> queryList = new ArrayList<>();
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.parse(file, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements queries = doc.getElementsByTag("top");
        for (Element q : queries) {
            Query query = new Query(q);
            queryList.add(query);
        }
        return queryList;
    }

    /**
     * this function adding to the query string semnatic words from the model word2vecmodel
     * @param query
     */
    public void addSemanticToQuery(Query query) {
        if (semantic) { //add related word to the query
            Word2VecModel model = null;
            try {
                model = Word2VecModel.fromTextFile(new File("word2vec.c.output.model.txt"));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            com.medallia.word2vec.Searcher semanticSearcher = model.forSearch();
            String queryString = query.getQueryString();
            String[] arrayOfQuery = queryString.split(" ");
            for (int i = 0; i < arrayOfQuery.length; i++) {
                try {

                    List<com.medallia.word2vec.Searcher.Match> matches = semanticSearcher.getMatches(arrayOfQuery[i], 1);
                    for (com.medallia.word2vec.Searcher.Match match : matches) {
                        query.addStringToQuery(match.match());
                    }
                } catch (com.medallia.word2vec.Searcher.UnknownWordException e) {
                    // NOT KNOWN
                }
            }
        }
    }

    public void setSemantic(boolean semantic) {
        this.semantic = semantic;
    }

    public void setSearchEntity(boolean searchEntity) {
        this.searchEntity = searchEntity;
    }

    public void setQueryPath(String queryPath) {
        this.queryPath = queryPath;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

}
