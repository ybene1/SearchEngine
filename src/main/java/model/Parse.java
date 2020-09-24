package model;

import opennlp.tools.stemmer.PorterStemmer;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * this class represent the Parse class that parses the text in the files
 */
public class Parse {

    private PorterStemmer stemmer; // the stemmer of this class
    private List<String> termList; // array list of the terms after parse
    private HashMap<String, String> monthTable; // all the months
    private String[] arrayOfText; // the array of the text split by delimiters - before parse
    private int currentPosition; // the current positing, index parsing the arrayOfText
    private String puncRegex;
    private boolean withStemming;
    private HashSet<String> stopWords;
    private HashMap<String, Term> termHashMap;
    private HashMap<String, Term> entityHashMap;
    private boolean stopChecking;


    /**
     * the constructor of the Parse
     * @param stopWordsPath
     */
    public Parse(String stopWordsPath) {
        stemmer = new PorterStemmer();
        initializeMonthTable();
        stopChecking = false;
        this.puncRegex = " |\n|\"|\r|\\Q(\\E|\\Q)\\E|\\Q{\\E|\\Q}\\E|\\Q;\\E|\\Q:\\E|\\|\'|\\Q*\\E|\\Q!\\E|\\Q@\\E|\\Q#\\E|\\Q^\\E|\\Q&\\E|\\Q[\\E|\\Q]\\E|\\Q?\\E|\\Q|\\E|\\Q~\\E|\\Q`\\E|\t|\\Q /\\E|\\Q/ \\E|\\Q. \\E|\\.\"|\"\\.|\\Q..\\E|\\Q .\\E|\\.\n|\\.\t|\\Q--\\E|\\Q$ \\E|\\Q+\\E";
        stopWords = new HashSet<>();
        try {
            File f = new File(stopWordsPath);
            FileReader fileReader = new FileReader(f);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stopWords.add(line.toUpperCase());
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * partB
     * this function gets query and parse it
     * @param query
     */
    public void parseQuery(Query query,boolean stemming){
        ArrayList<Term> res = parseTextHelper(query.getQueryString(),stemming);
        query.setListOfTerms(res);
        ArrayList<Term> entity = new ArrayList<>();
        entity.addAll(entityHashMap.values());
        query.setListOfEntity(entity);
    }

    private ArrayList<Term> parseTextHelper(String text,boolean stemming){
        this.stopChecking = false;
        this.termList = new ArrayList<>();
        this.termHashMap = new HashMap<>();
        this.entityHashMap = new HashMap<>();
        this.currentPosition = 0;
        this.withStemming = stemming;
        text = text.replace("--", "");
        text = text.replace("-", " - ");
        text = text.replace(",", "");
        this.arrayOfText = text.split(puncRegex);
        for (int i = currentPosition; i < arrayOfText.length; i++) {
            if (this.arrayOfText[i].equals("")) {
                continue;
            } else if (!this.arrayOfText[i].toUpperCase().equals("BETWEEN")
                    && !this.arrayOfText[i].toUpperCase().equals("MAY")) {
                if ((stopWords.contains(this.arrayOfText[i].toUpperCase()))) {
                    continue;
                }
            }
            if (isWordMainFunction(i)) {
            } else if (stopChecking) {
                stopChecking = false;
            } else if (isNumberMainFunction(i)) {
            } else {
                continue;
            }
            if (i != currentPosition) {
                i = currentPosition;
            }
        }

        ArrayList<Term> res = new ArrayList<Term>();
        res.addAll(termHashMap.values());
        return res;
    }


    /**
     * this is the main function of the parse
     * @param document - sets a list of terms by the document text
     * @param stemming - if the stemming is true then the parse will be with stemmer
     */
    public void parseText(Document document, boolean stemming) {
        String text = document.getTEXT();
        ArrayList<Term> res = parseTextHelper(text,stemming);
        document.setSizeOfDoc(arrayOfText.length);
        document.setListOfTerms(res);
        document.setUniqueTerms(res.size());
        ArrayList<Term> entity = new ArrayList<>();
        entity.addAll(entityHashMap.values());
        document.setListOfEntity(entity);
    }

    /**
     * init the month hash map
     */
    private void initializeMonthTable() {
        this.monthTable = new HashMap<String, String>();
        monthTable.put("JANUARY", "01");
        monthTable.put("FEBRUARY", "02");
        monthTable.put("MARCH", "03");
        monthTable.put("APRIL", "04");
        monthTable.put("MAY", "05");
        monthTable.put("JUNE", "06");
        monthTable.put("JULY", "07");
        monthTable.put("AUGUST", "08");
        monthTable.put("SEPTEMBER", "09");
        monthTable.put("OCTOBER", "10");
        monthTable.put("NOVEMBER", "11");
        monthTable.put("DECEMBER", "12");
        //only 3 letters - prefix
        monthTable.put("JAN", "01");
        monthTable.put("FEB", "02");
        monthTable.put("MAR", "03");
        monthTable.put("APR", "04");
        monthTable.put("MAY", "05");
        monthTable.put("JUN", "06");
        monthTable.put("JUL", "07");
        monthTable.put("AUG", "08");
        monthTable.put("SEP", "09");
        monthTable.put("OCT", "10");
        monthTable.put("NOV", "11");
        monthTable.put("DEC", "12");
    }

    /**
     * this function taking care of regular words
     * @param currentPosition
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    private boolean isWordMainFunction(int currentPosition) {
        if (monthTable.containsKey(this.arrayOfText[currentPosition].toUpperCase())) {
            if (monthDay(currentPosition) || yearMonthPattern(currentPosition)) {
                return true;
            } else {
                if (Character.isUpperCase(this.arrayOfText[currentPosition].charAt(0))) {
                    saveTerms(this.arrayOfText[currentPosition].toUpperCase(), currentPosition, currentPosition, TermType.word);
                }
                else{
                    saveTerms(this.arrayOfText[currentPosition], currentPosition, currentPosition, TermType.word);
                }
            }
        }
        return (regularWord(currentPosition) || stopChecking || rangeWordPattern(currentPosition) ||
                wordWithDot(currentPosition) || wordWithFraction(currentPosition)
                || numberSTnumberTH(currentPosition));
    }

    /**
     * this function taking care of the patter "number'st" for exp: 1'st
     * or "number'th" for exp: 1'th
     * @param startIndex
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    private boolean numberSTnumberTH(int startIndex) {
        if (this.arrayOfText[startIndex].matches(("^((\\d+)(st))$"))
                || this.arrayOfText[startIndex].matches(("^((\\d+)(th))$")) ||
                this.arrayOfText[startIndex].matches(("^((\\d+)(St))$"))) {
            saveTerms(this.arrayOfText[startIndex], startIndex, startIndex, TermType.number);
            return true;
        }
        return false;
    }

    private boolean wordWithFraction(int startIndex) {
       /* if(this.arrayOfText[startIndex].matches
                (("^((([a-zA-Z]+)([/][a-zA-Z]+)))$")) ||
                this.arrayOfText[startIndex].matches
                        (("^((([a-zA-Z0-9]+)([/][a-zA-Z0-9]+)))$")) ||
                this.arrayOfText[startIndex].matches
                        ("^((([a-zA-Z0-9]+)([/][a-zA-Z0-9]+))+([/][a-zA-Z0-9]+)*)$")) {
            saveTerms(this.arrayOfText[startIndex],startIndex,startIndex,TermType.word);
            return true;
        }
        */
        return false;
    }

    /**
     * Between number and number template, save as 1 term
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[startIndex] until arrayOfText[endIndex] is in this pattern
     */
    public boolean betweenPattern(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        if (this.arrayOfText[startIndex].toUpperCase().equals("BETWEEN")) {
            if (nextIndex < arrayOfText.length) {
                if (getPureRegularNumber(nextIndex)) {
                    nextIndex++;
                    if (nextIndex < arrayOfText.length) {
                        if (this.arrayOfText[nextIndex].equals("and")) {
                            nextIndex++;
                        }
                        if (nextIndex < arrayOfText.length) {
                            if (getPureRegularNumber(nextIndex)) {
                                // we got to correct template!
                                // the numbers already been saved at regauleNumber function
                                // need to save here 1 terms:
                                //  "Between number and number"
                                if (termList.size() < 2) {
                                    return false;
                                }
                                StringBuilder first = new StringBuilder();
                                first.append("Between ");
                                first.append(termList.get(termList.size() - 2));
                                first.append(" and ");
                                first.append(termList.get(termList.size() - 1));
                                saveTerms(first.toString(), startIndex, nextIndex, TermType.word);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Word-word
     * Word-word-word
     * number-word
     * number-number
     * saving as complete term, and each term separate
     * @param startIndex
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    public boolean rangeWordPattern(int startIndex) {
        int nextIndex = startIndex + 1; // 1
        boolean thirdWord = false;
        // template = Word-word or Word-word-word
        // save as one term
        if (arrayOfText[startIndex].equals("-")) {
            if (nextIndex < arrayOfText.length) {
                if (getPureRegularNumber(nextIndex) || regularWord(nextIndex) ||
                        wordWithDot(nextIndex)) {
                    // checks if its three word-word-word term
                    nextIndex++; // 2
                    if (nextIndex < arrayOfText.length) {
                        if (arrayOfText[nextIndex].equals("-")) {
                            nextIndex++;
                            if (nextIndex < arrayOfText.length) {
                                if (regularWord(nextIndex) || getPureRegularNumber(nextIndex) ||
                                        wordWithDot(nextIndex)) {
                                    thirdWord = true;
                                }
                            }
                        }
                    }
                    StringBuilder term = new StringBuilder();
                    if (!thirdWord) {
                        if (this.termList.size() >= 2) {
                            term.append(this.termList.get(this.termList.size() - 2));
                            term.append("-");
                            term.append(this.termList.get(this.termList.size() - 1));
                            nextIndex = startIndex + 1;
                        }
                    } else {
                        if (this.termList.size() >= 3) {
                            term.append(this.termList.get(this.termList.size() - 3));
                            term.append(this.termList.get(this.termList.size() - 2));
                            term.append(this.termList.get(termList.size() - 1));
                        }
                    }
                    if (!term.equals("")) {
                        saveTerms(term.toString(), startIndex - 1, nextIndex, TermType.ragne);
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * taking care of the potential entity pattern - a sequence of big letters combination
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[startIndex] until arrayOfText[endIndex] is in this pattern
     */
    public boolean entityPattern(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        StringBuilder exp = new StringBuilder();
        // entity needs to be at least two words
        if (this.arrayOfText[startIndex].matches("^([A-Z])([a-zA-Z]*)$")) {
            if (nextIndex < arrayOfText.length) {
                if (this.arrayOfText[nextIndex].matches("^([A-Z])([a-zA-Z]*)$")) {
                    upperCaseWord(startIndex);
                    exp.append(this.arrayOfText[startIndex].toUpperCase());
                    int index = nextIndex;
                    while ((index < arrayOfText.length) &&(arrayOfText[index].equals("") || upperCaseWord(index))) {
                        if(arrayOfText[index].equals("")){
                            index++;
                            continue;
                        }
                        exp.append(" "+this.arrayOfText[index].toUpperCase());
                        index++;
                    }
                    saveTerms(exp.toString(),startIndex,index-1,TermType.entity);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this function checks if the pattern is a regular word that start with big letter
     * @param startIndex
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    public boolean upperCaseWord(int startIndex) {
        if (this.arrayOfText[startIndex].matches("^([A-Z])([a-zA-Z]*)$")) {
            String stemmed;
            if (this.withStemming) {
                stemmed = stemmer.stem(this.arrayOfText[startIndex].toLowerCase());
            } else {
                stemmed = this.arrayOfText[startIndex].toUpperCase();
            }
            if (stemmed.equals("")) {
                return true;
            }
            saveTerms(stemmed.toUpperCase(), startIndex, startIndex, TermType.word);
            return true;
        }
        return false;
    }


    /**
     * this function taking care for regular words.
     * it might be -
     * entityWord or betweenWordpattern or regular word
     * it can not be Word-Word because it wont fill the regular exp
     * @param startIndex
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    public boolean regularWord(int startIndex) {
        if (this.arrayOfText[startIndex].matches("^([a-zA-Z]+)$")) {
            if (optionsForWords(startIndex, startIndex)) {
                return true;
            } else {
                // you are regular single!!! word
                String stemmed;
                if (this.withStemming) {
                    stemmed = stemmer.stem(this.arrayOfText[startIndex].toLowerCase());
                } else {
                    stemmed = this.arrayOfText[startIndex].toLowerCase();
                }
                if (stemmed.equals("")) {
                    stopChecking = true;
                    currentPosition = startIndex;
                    return false;
                }
                if (Character.isUpperCase(this.arrayOfText[startIndex].charAt(0))) {
                    stemmed = stemmed.toUpperCase();
                }
                saveTerms(stemmed, startIndex, startIndex, TermType.word);
                return true;
            }
        }
        return false;
    }

    /**
     * this function is the function that checks the option of word template
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[startIndex] until arrayOfText[endIndex] is in this pattern
     */
    public boolean optionsForWords(int startIndex, int endIndex) {
        return (betweenPattern(startIndex, endIndex) || entityPattern(startIndex, endIndex));
    }

    private boolean wordWithDot(int startIndex) {
//        if(this.arrayOfText[startIndex].matches(("^((([a-zA-Z][.])|([a-zA-Z]))*[.]([a-zA-Z]))$"))){
//            saveTerms(this.arrayOfText[startIndex],startIndex,startIndex,TermType.number);
//            return true;
//        }
        return false;

    }

    /**
     * this function taking care of the date pattern - month year - 1994 May
     * @param indexInArray
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    private boolean yearMonthPattern(int indexInArray) {
        if (monthTable.containsKey(this.arrayOfText[indexInArray].toUpperCase())) {
            int nextIndex = indexInArray + 1;
            if (nextIndex < this.arrayOfText.length) {
                if (this.arrayOfText[nextIndex].matches("(\\d){4}")) {
                    if (isYear(this.arrayOfText[nextIndex])) {
                        // template = month year , e.g: June 1994, May 2014
                        // save as = year-month , e.g: 1994-06 , 2014-05
                        saveTerms(this.arrayOfText[nextIndex] + "-" +
                                        monthTable.get(this.arrayOfText[indexInArray].toUpperCase()),
                                indexInArray, nextIndex, TermType.date);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * this function taking care of the date pattern - month day , May 5
     * @param indexInArray
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    private boolean monthDay(int indexInArray) {
        if (monthTable.containsKey(this.arrayOfText[indexInArray].toUpperCase())) {
            int nextIndex = indexInArray + 1;
            if (nextIndex < this.arrayOfText.length) {
                if (this.arrayOfText[nextIndex].matches("(\\d){1,2}")) {
                    if (ifDayInMonth(this.arrayOfText[nextIndex])) {
                        // template = month number , e.g: June 4, May 14
                        // save as = month-day , e.g: 06-04 , 05-14

                        //check if we need zero before the numbers
                        if (this.arrayOfText[nextIndex].length() == 1) {
                            saveTerms(this.monthTable.get(this.arrayOfText[indexInArray].toUpperCase()) +
                                            "-0" + this.arrayOfText[nextIndex], indexInArray, nextIndex,
                                    TermType.date);
                        } else { // month without zero , e.g "12,11,10"
                            saveTerms(this.monthTable.get(this.arrayOfText[indexInArray].toUpperCase()) +
                                            "-" + this.arrayOfText[nextIndex], indexInArray, nextIndex,
                                    TermType.date);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * this function determinate if a number is valid year
     * @param s
     * @return true if its valid year
     */
    private boolean isYear(String s) {
        try {
            int dayOrYear = Integer.parseInt(s);
            return ((dayOrYear >= 1000 && dayOrYear <= 9999));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * this function checks if a number is a valid day in month
     * @param s
     * @return true if its a day in month
     */
    private boolean ifDayInMonth(String s) {
        try {
            int day = Integer.parseInt(s);
            return ((day >= 1 && day <= 31));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * this function taking care of the date pattern - day month , exp: 4 May
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[startIndex] until arrayOfText[endIndex] is in this pattern
     */
    private boolean DDMonthPattern(int startIndex, int endIndex) {
        int checker = endIndex + 1;
        if ((arrayOfText[startIndex].matches("(\\d){1,2}"))) {
            if (ifDayInMonth(arrayOfText[startIndex])) {
                if (checker < arrayOfText.length) {
                    StringBuilder tmp = new StringBuilder();
                    tmp.append(arrayOfText[checker].toUpperCase());
                    if (this.monthTable.containsKey(tmp.toString())) {
                        endIndex = checker;
                        // template = number month (the number is with zero) ,e.g: 4 May
                        // save as = month-day , e.g: 05-04
                        if (arrayOfText[startIndex].length() == 1) {
                            saveTerms(monthTable.get(arrayOfText[checker].toUpperCase()) + "-" + "0" + arrayOfText[startIndex], startIndex,
                                    endIndex, TermType.date);
                            return true;
                        }
                        // template = number month , e.g: 12 December
                        // save as = month-day , e.g: 12-12
                        saveTerms(monthTable.get(arrayOfText[checker].toUpperCase()) + "-" + arrayOfText[startIndex], startIndex,
                                endIndex, TermType.date);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** this pattern is the percent pattern:
     * number%
     * number percent
     * number percentage
     * @param startPosition
     * @param endPosition
     * @return true if the arrayOfText[startIndex] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isPercent(int startPosition, int endPosition) {
        int nextIndex = endPosition + 1;
        if (inputCheckForIndex(startPosition, nextIndex)) {
            if (this.arrayOfText[nextIndex].matches("%") ||
                    this.arrayOfText[nextIndex].matches("percent") ||
                    this.arrayOfText[nextIndex].matches("percentage")) {
                endPosition = nextIndex;
                // save % template
                saveTerms(arrayOfText[startPosition] + "%", startPosition, endPosition, TermType.percent);
                return true;
            }
        }
        return false;
    }


    /**
     * this function checks if the input index are legals in the bounds of the array.
     * @param startPosition
     * @param endPosition
     * @return
     */
    public boolean inputCheckForIndex(int startPosition, int endPosition) {
        if (endPosition < this.arrayOfText.length && endPosition >= 0
                && startPosition >= 0 && startPosition < this.arrayOfText.length) {
            return true;
        }
        return false;
    }

    /**
     * this function is the main function for numbers
     * @param indexInArray
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    public boolean isNumberMainFunction(int indexInArray) {
        return (regularNumber(indexInArray) || numberWithDot(indexInArray) ||
                numberWithFraction(indexInArray, indexInArray) || numberWithLinkedPercent(indexInArray) ||
                numberWithLinkedDollar(indexInArray));
    }

    /**
     * this function taking care of number with linked dollar sign ("$") pattern
     * @param startIndex
     * @return true if the arrayOfText[startIndex] is in this pattern
     */
    private boolean numberWithLinkedDollar(int startIndex) {
        int nextIndex = startIndex + 1;
        // template = $number (the number is with dot) - e.g: $2223.32
        if (this.arrayOfText[startIndex].matches("[$](\\d+)[.](\\d+)")) {
            double d = Double.parseDouble(this.arrayOfText[startIndex].substring(1));
            boolean upThenMillion = false;
            if (d > 1000000) {
                d /= 1000000;
                upThenMillion = true;
            }
            String name = convertTheDouble(d + "");
            // template = $number (number is with dot and greater then 1,000,000)
            // save as = number M Dollars
            if (upThenMillion) {
                saveTerms(name + " M Dollars", startIndex, startIndex, TermType.dollars);
            } else { // template = $number (number is with dot and less then 1,000,000)
                // save as = number Dollars
                name = addingPuncToNumber(name);
                saveTerms(name + " Dollars",
                        startIndex, startIndex, TermType.dollars);
            }
            return true;
        }
        // template = $number (the number is without dot) - e.g: $2223 , $100,000 ...

        else if (this.arrayOfText[startIndex].matches("[$](\\d+)")) {
            // template = $number million
            // save as = number M Dollars
            if (nextIndex < this.arrayOfText.length) {
                if (this.arrayOfText[nextIndex].toUpperCase().equals("MILLION")) {
                    saveTerms(this.arrayOfText[startIndex].substring(1) + " M Dollars",
                            startIndex, nextIndex, TermType.dollars);
                }
                // template = $number billion
                // save as = number M Dollars
                else if (this.arrayOfText[nextIndex].toUpperCase().equals("BILLION")) {
                    // temp string is without "$" sign
                    String temp = this.arrayOfText[startIndex].substring(1);
                    double d = Double.parseDouble(temp);
                    d = d * 1000;
                    String name = convertTheDouble(d + "");
                    saveTerms(name + " M Dollars",
                            startIndex, nextIndex, TermType.dollars);
                } else {
                    // template = $number
                    // save as = number Dollars
                    String name = this.arrayOfText[startIndex].substring(1);
                    name = addingPuncToNumber(name);
                    saveTerms(name + " Dollars",
                            startIndex, startIndex, TermType.dollars);
                }
            } else {
                // template = $number
                // save as = number Dollars
                double d = Double.parseDouble(this.arrayOfText[startIndex].substring(1));
                boolean upThenMillion = false;
                if (d > 1000000) {
                    d /= 1000000;
                    upThenMillion = true;
                }
                String name = convertTheDouble(d + "");
                // template = $number (number is with dot and greater then 1,000,000)
                // save as = number M Dollars
                if (upThenMillion) {
                    saveTerms(name + " M Dollars", startIndex, startIndex, TermType.dollars);
                } else { // template = $number (number is with dot and less then 1,000,000)
                    // save as = number Dollars
                    name = addingPuncToNumber(name);
                    saveTerms(name + " Dollars",
                            startIndex, startIndex, TermType.dollars);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * this function removes the number after the dot and leaves only 3 nubmers after the dot
     * @param name
     * @return the string after the chagnes
     */
    private String addingPuncToNumber(String name) {
        int indexOfDot = name.indexOf(".");
        String temp = name;
        if (indexOfDot > 0) {
            temp = name.substring(0, indexOfDot);
        }
        if (temp.length() > 3 && temp.length() < 7) {
            temp = temp.substring(0, temp.length() - 3) + "," + temp.substring(temp.length() - 3);
            if (indexOfDot > 0) {
                temp = temp + name.substring(indexOfDot);
            }
            name = temp;
        }
        return name;
    }

    /**
     * this function taking care for the template of regular number
     * @param indexInArray
     * @return true if the arrayOfText[indexInArray] is in this pattern
     */
    private boolean regularNumber(int indexInArray) {
        long number = 0;
        try {
            // if not of all other options - im adding him otherwise, the function already save them
            number = Long.parseLong(this.arrayOfText[indexInArray]); // this is a number
            if (optionsForRegularNumber(indexInArray)) {
                return true;
            } else {
                getPureRegularNumber(indexInArray);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * this function taking care for pure regular number - without "%" or "$" or "Dollars"... patterns.
     * @param indexInArray
     * @return true if the arrayOfText[indexInArray] is in this pattern
     */
    private boolean getPureRegularNumber(int indexInArray) {
        long number = 0;
        try {
            number = Long.parseLong(this.arrayOfText[indexInArray]); // this is a number
            // IM JUST A NUMBER:
            // save the normal integer number
            if (number < 999) {
                saveTerms(number + "", indexInArray, indexInArray, TermType.number);
            }
            // save number with K
            else if (number > 999 && number < 999999) {
                double d = number;
                d = d / 1000;
                String name = convertTheDouble(d + "");
                saveTerms(name + "K", indexInArray, indexInArray, TermType.number);
            }
            //save number with M
            else if (number > 999999 && number < 999999999) {
                double d = number;
                d = d / 1000000;
                String name = convertTheDouble(d + "");
                saveTerms(name + "M", indexInArray, indexInArray, TermType.number);
            }
            //save number with B
            else {
                double d = number;
                d = d / 1000000000;
                String name = convertTheDouble(d + "");
                saveTerms(name + "" + "B", indexInArray, indexInArray, TermType.number);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * this function determinate the template number% without spaces
     * @param indexInArray
     * @return true if the template is nubmer%
     */
    public boolean numberWithLinkedPercent(int indexInArray) {
        if (this.arrayOfText[indexInArray].matches("(\\d+)[.](\\d+)[%]")) {
            double d = Double.parseDouble(this.arrayOfText[indexInArray].substring(0,
                    this.arrayOfText[indexInArray].length() - 1));
            String name = convertTheDouble(d + "");
            saveTerms(name + "%", indexInArray, indexInArray, TermType.percent);
            return true;
        } else if (this.arrayOfText[indexInArray].matches("(\\d+)[%]")) {
            saveTerms(this.arrayOfText[indexInArray], indexInArray, indexInArray, TermType.percent);
            return true;
        }
        return false;
    }


    /**
     * this function gets a string that the presentation of it is double number
     * The function leaves 3 numbers after the dot. also, she removes zeros after the dot if
     * there is some.
     * @param doubleToConvert
     * @return the string after the chagnes
     */
    private String convertTheDouble(String doubleToConvert) {
        int indexOfDot = doubleToConvert.indexOf(".");
        doubleToConvert = doubleToConvert.substring(0, Math.min(indexOfDot + 4, doubleToConvert.length()));
        char[] chars = doubleToConvert.toCharArray();
        int i = 0;
        for (i = Math.min(indexOfDot + 3, doubleToConvert.length() - 1); i > indexOfDot; i--) {
            if (chars[i] == '0') {
                doubleToConvert = doubleToConvert.substring(0, i);
            } else {
                break;
            }
        }
        if (i == indexOfDot) {
            doubleToConvert = doubleToConvert.substring(0, indexOfDot);
        }
        return doubleToConvert;
    }

    /**
     * this function taking care of all the options for numbers
     * @param startIndex
     * @return true if the arrayOfText[indexInArray] is in this pattern
     */
    private boolean optionsForRegularNumber(int startIndex) {
        return ((isPercent(startIndex, startIndex) || DDMonthPattern(startIndex, startIndex) ||
                isNumWithMilBilTho(startIndex, startIndex) || isTheNextWordIsFraction(startIndex, startIndex) || isDollars(startIndex, startIndex)));
    }

    /**
     * this function taking care of all the options for numbers with dollars
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isDollars(int startIndex, int endIndex) {
        return optionsForDollars(startIndex, endIndex);
    }

    /**
     * this function taking care of all the options for numbers with dollars
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean optionsForDollars(int startIndex, int endIndex) {
        return (isTheNextWordIsDollars(startIndex, endIndex) || isPriceMBnDollars(startIndex, endIndex) || isTrillion(startIndex, endIndex));
    }

    /**
     * this function taking care of all the options for numbers (decimal) with dollars
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean optionsForDollarsNumWithDot(int startIndex, int endIndex) {
        return (isTheNextWordIsDollars(startIndex, endIndex) || isPriceMBnDollars(startIndex, endIndex) || isTrillion(startIndex, endIndex));
    }

    /**
     * this function taking care of number with M or BN word after it
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isPriceMBnDollars(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        if (nextIndex < this.arrayOfText.length) {
            if (this.arrayOfText[nextIndex].toUpperCase().equals("M")) {
                if (isTheNextWordIsDollars(startIndex, nextIndex)) {
                    return true;
                }
            } else if (this.arrayOfText[nextIndex].toUpperCase().equals("BN")) {
                if (isTheNextWordIsDollars(startIndex, nextIndex)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * this function taking care of number with TRILLION word after it
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isTrillion(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        if (nextIndex < this.arrayOfText.length) {
            if (this.arrayOfText[nextIndex].toUpperCase().equals("TRILLION")) {
                endIndex = nextIndex;
                if (isTheNextWordIsUsDollars(startIndex, endIndex)) {
                    endIndex += 2;
                    // template = NUMBER trillion U.S DOLLARS
                    // save as = NUMBER M DOLLARS
                    // converting trillion to million by multiply the number with 1,000,000
                    Double d = Double.parseDouble(this.arrayOfText[startIndex]);
                    d *= 1000000;
                    BigDecimal decimal = BigDecimal.valueOf(d);
                    String name = convertTheDouble(decimal + "");
                    saveTerms(name + " M Dollars", startIndex, endIndex, TermType.dollars);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * This function determinate if the next expression is fraction
     * @param startIndex
     * @param endIndex
     * @return true if the next exp is fraction
     */
    private boolean isTheNextWordIsFraction(int startIndex, int endIndex) {
        if (this.arrayOfText[startIndex].matches("(\\d+){7,100}")) {
            return false;
        }
        if (endIndex < this.arrayOfText.length) {
            if (numberWithFraction(startIndex, endIndex + 1)) {
                return true;
            }
        }
        return false;
    }


    /**
     * this function taking care of number with fraction pattern - for exp: 3/4
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean numberWithFraction(int startIndex, int endIndex) {
        if (endIndex < this.arrayOfText.length) {
            if (this.arrayOfText[endIndex].matches("(\\d+)[/](\\d+)")) {
                if (!isTheNextWordIsDollars(startIndex, endIndex)) {
                    // template = number fraction or fraction , e.g: 34 3/4 , 3/4
                    // save as = number fraction
                    if (startIndex != endIndex) {
                        saveTerms(this.arrayOfText[startIndex] + " " + arrayOfText[endIndex], startIndex, endIndex,
                                TermType.number);
                    } else {
                        // save as = fraction
                        saveTerms(this.arrayOfText[startIndex], startIndex, endIndex,
                                TermType.number);
                    }
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * main function for number with miliion billion trillion word after it
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isNumWithMilBilTho(int startIndex, int endIndex) {
        return (isMillion(startIndex, endIndex) || isBillion(startIndex, endIndex) || isThousand(startIndex, endIndex));

    }

    /**
     * this function taking care of number with dot pattern (decimal)
     * @param indexInArray
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean numberWithDot(int indexInArray) {
        if (this.arrayOfText[indexInArray].matches("(\\d+)[.](\\d+)")) {
            this.arrayOfText[indexInArray] = convertTheDouble(this.arrayOfText[indexInArray]);
            if (!(isPercent(indexInArray, indexInArray) || isNumWithMilBilTho(indexInArray, indexInArray) || optionsForDollarsNumWithDot(indexInArray, indexInArray))) {
                // template = number (the type is number with dot), e.g: 5.4 , 100000.324...
                // save as = number
                double number = Double.parseDouble(this.arrayOfText[indexInArray]);
                if (number < 999) {
                    saveTerms(number + "", indexInArray, indexInArray, TermType.number);
                }

                // save number with K
                else if (number > 999 && number < 999999) {
                    double d = number;
                    d = d / 1000;
                    String name = convertTheDouble(d + "");
                    saveTerms(name + "K", indexInArray, indexInArray, TermType.number);
                }

                //save number with M
                else if (number > 999999 && number < 999999999) {
                    double d = number;
                    d = d / 1000000;
                    String name = convertTheDouble(d + "");
                    saveTerms(name + "M", indexInArray, indexInArray, TermType.number);
                }
                //save number with B
                else {
                    double d = number;
                    d = d / 1000000000;
                    String name = convertTheDouble(d + "");
                    saveTerms(name + "" + "B", indexInArray, indexInArray, TermType.number);
                }
                return true;
            }
            return true;
        }
        return false;
    }

    /**
     * this function taking care of numbers with the word THOUSAND after it , for exp:
     * 1 thousand
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isThousand(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        if (nextIndex < this.arrayOfText.length) {
            if (this.arrayOfText[nextIndex].toUpperCase().equals("THOUSAND")) {
                endIndex = nextIndex;
                // template = NUMBER THOUSAND
                // save as = NumberK
                saveTerms(this.arrayOfText[startIndex] + "K", startIndex, endIndex, TermType.number);
                return true;
            }
        }
        return false;
    }

    /**
     * this function taking care of numbers with the word MILLION after it , for exp:
     * 1 million
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isMillion(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        if (nextIndex < this.arrayOfText.length) {
            if (this.arrayOfText[nextIndex].toUpperCase().equals("MILLION")) {
                endIndex = nextIndex;
                if (isTheNextWordIsUsDollars(startIndex, endIndex)) {
                    endIndex += 2;
                    // template = NUMBER MILLION U.S DOLLARS
                    // save as = NUMBER M DOLLARS
                    saveTerms(this.arrayOfText[startIndex] + " M Dollars", startIndex, endIndex, TermType.dollars);
                    return true;
                } else {
                    // template = NUMBER MILLION
                    // save as = NumberM
                    saveTerms(this.arrayOfText[startIndex] + "M", startIndex, endIndex, TermType.number);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this function taking care of numbers with the word U.S DOLLARS after it
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isTheNextWordIsUsDollars(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        if (nextIndex < this.arrayOfText.length) {
            if (this.arrayOfText[nextIndex].toUpperCase().equals("U.S")) {
                nextIndex++;
                if (nextIndex < this.arrayOfText.length) {
                    return (this.arrayOfText[nextIndex].toUpperCase().equals("DOLLARS"));
                }
            }
        }
        return false;
    }

    /**
     * this function taking care of numbers with the word BILLION after it , for exp:
     * 1 billion
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isBillion(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        if (nextIndex < this.arrayOfText.length) {
            if (this.arrayOfText[nextIndex].toUpperCase().equals("BILLION")) {
                endIndex = nextIndex;
                if (isTheNextWordIsUsDollars(startIndex, endIndex)) {
                    endIndex += 2;
                    // template = NUMBER billion U.S DOLLARS
                    // save as = NUMBER M DOLLARS
                    // converting billion to million by multiply the number with 1,000
                    double d = Double.parseDouble(this.arrayOfText[startIndex]);
                    d = d * 1000;
                    String name = convertTheDouble(d + "");
                    saveTerms(name + " M Dollars", startIndex, endIndex, TermType.dollars);
                    return true;
                } else {
                    // template = NUMBER Billion
                    // save as = NumberB
                    saveTerms(this.arrayOfText[startIndex] + "B", startIndex, endIndex, TermType.number);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * this function detemintates if the next word is "DOllars", and if so -
     * save it as the correct pattern
     * @param startIndex
     * @param endIndex
     * @return true if the arrayOfText[indexInArray] until arrayOfText[endIndex] is in this pattern
     */
    private boolean isTheNextWordIsDollars(int startIndex, int endIndex) {
        int nextIndex = endIndex + 1;
        if (nextIndex < this.arrayOfText.length) {
            if (!this.arrayOfText[nextIndex].toUpperCase().matches("DOLLARS")) {
                return false;
                // template - Price m Dollars
                // what we save - NUMBER M DOLLARS
            } else if (this.arrayOfText[endIndex].toUpperCase().equals("M")) {
                saveTerms(this.arrayOfText[startIndex] + " M " + "Dollars", startIndex,
                        nextIndex, TermType.dollars);
                // template - Price bn Dollars
                // what we save - NUMBER M DOLLARS
            } else if (this.arrayOfText[endIndex].toUpperCase().equals("BN")) {
                double number = Double.parseDouble(this.arrayOfText[startIndex]);
                number *= 1000;
                String exp = convertTheDouble(number + "");
                saveTerms(exp + " M " + "Dollars", startIndex,
                        nextIndex, TermType.dollars);
            }
            //template = number fraction Dollars
            // save as = number fraction Dollars
            else if (this.arrayOfText[endIndex].matches("(\\d+)[/](\\d+)")) {
                String exp = "";
                if (this.arrayOfText[startIndex].matches("(\\d+){7,100}[.](\\d+)")) {
                    return false;
                } else if (this.arrayOfText[startIndex].matches("(\\d+){7,100}")) {
                    return false;
                }
                for (int i = startIndex; i <= endIndex; i++) {
                    exp = exp + this.arrayOfText[i] + " ";
                }
                saveTerms(exp + "Dollars",
                        startIndex, nextIndex, TermType.dollars);
            } else {

                // template = PRICE DOLLARS, e.g: 1000000 Dollars, 1000000322 Dollars...
                // what we save = NUMBER M DOLLARS
                if (this.arrayOfText[endIndex].matches("((\\d+){7,100})[.](\\d+)")) {
                    double d = Double.parseDouble(this.arrayOfText[endIndex]);
                    d = d / 1000000;
                    String name = convertTheDouble(d + "");
                    saveTerms(name + " M Dollars", startIndex, nextIndex, TermType.dollars);
                } else if (this.arrayOfText[endIndex].matches("(\\d+){7,100}")) {
                    double d = Double.parseDouble(this.arrayOfText[endIndex]);
                    d = d / 1000000;
                    String name = convertTheDouble(d + "");
                    saveTerms(name + " M Dollars", startIndex, nextIndex, TermType.dollars);
                } else { // your are small then 1,000,000, saving without "M"
                    saveTerms(this.arrayOfText[endIndex] + " Dollars", startIndex, nextIndex, TermType.dollars);

                }
            }
            return true;
        }
        return false;
    }


    /**
     * this function gets inputs for new term.The function checks if the
     * hash map of terms contains term with the same name - and if so,
     * she doesnt adds him to the hash map
     * this function as well adds this term to the list so we will have
     * the right order of inserting terms, we use this order for some functions.
     * @param exp
     * @param startIndex
     * @param endIndex
     * @param termType
     */
    public void saveTerms(String exp, int startIndex, int endIndex, TermType termType) {
        if (exp.equals("")) {
            return;
        }
        this.currentPosition = endIndex;
        Term term = new Term(termType, exp);
        if(term.getTermType().equals(TermType.entity)){
            if (!entityHashMap.containsKey(exp)) {
                entityHashMap.put(exp, term);
            } else {
                // updating the total_tf for the term
                entityHashMap.get(exp).setTotaTf(1);
            }
        }
        else{
            termList.add(exp);
            if (!termHashMap.containsKey(exp)) {
                termHashMap.put(exp, term);
            } else {
                // updating the total_tf for the term
                termHashMap.get(exp).setTotaTf(1);
            }
        }
    }
}