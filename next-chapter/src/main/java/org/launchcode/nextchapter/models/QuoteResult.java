package org.launchcode.nextchapter.models;

public class QuoteResult {
    private String q;
    private String a;


    public QuoteResult(String q, String a) {
        this.q = q;
        this.a = a;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
}
