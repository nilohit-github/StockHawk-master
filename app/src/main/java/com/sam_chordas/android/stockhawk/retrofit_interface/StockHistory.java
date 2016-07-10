package com.sam_chordas.android.stockhawk.retrofit_interface;

import com.sam_chordas.android.stockhawk.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jhani on 7/8/2016.
 */
public interface StockHistory{


    @GET("/public/yql")
    Call<Result> getHistoricalData(@Query("q") String q, @Query("diagnostics") String diagnostics,
                           @Query("env") String env, @Query("format") String format);
}