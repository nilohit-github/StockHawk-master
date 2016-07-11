package com.sam_chordas.android.stockhawk.retrofit_interface;

import com.sam_chordas.android.stockhawk.model.Stock;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jhani on 7/8/2016.
 */
public interface StockHistory{


    @GET("v1/public/yql?&format=json&diagnostics=true&env=store://datatables.org/alltableswithkeys&callback=")
    Call<List<Stock>> getHistoricalData(@Query("q") String query);



}