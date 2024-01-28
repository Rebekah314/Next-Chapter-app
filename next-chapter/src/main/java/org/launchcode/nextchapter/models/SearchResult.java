package org.launchcode.nextchapter.models;

import java.util.List;


//This class was created to handle the first layer of data returned by the json,
// viewable (with Lord of the Rings used as an example) here: https://openlibrary.org/search.json?q=the+lord+of+the+rings

//The relevant data, contained within a "docs" arraylist within this class, will be further parsed by the model SearchResultBook.

public class SearchResult {

    private int numFound;
    private List<SearchResultBook> docs;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public List<SearchResultBook> getDocs() {
        return docs;
    }

    public void setDocs(List<SearchResultBook> docs) {
        this.docs = docs;
    }
}
