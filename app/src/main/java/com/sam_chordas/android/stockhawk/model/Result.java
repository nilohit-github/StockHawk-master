package com.sam_chordas.android.stockhawk.model;

import java.util.List;

/**
 * Created by jhani on 7/8/2016.
 */
public class Result {


     List<StockItem> mStockItems;


        public List<StockItem> getStockItems() {
            return mStockItems;
        }

        public void setStockItems(List<StockItem> stockItems) {
            mStockItems = stockItems;
        }

        public static class StockItem {

            String Date;
            String Close;

            public StockItem(String date, String close) {
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
    }