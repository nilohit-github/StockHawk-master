package com.sam_chordas.android.stockhawk.model;

/**
 * Created by jhani on 7/8/2016.
 */
public class Stock {


    private String Date;
    private String Close;

    public Stock(String date, String close) {
        Date = date;
        Close = close;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getClose() {
        return Close;
    }

    public void setClose(String close) {
        this.Close = close;
    }


}
