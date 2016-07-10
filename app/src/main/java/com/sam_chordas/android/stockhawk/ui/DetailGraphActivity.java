package com.sam_chordas.android.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sam_chordas.android.stockhawk.model.QuoteDserielizer;
import com.sam_chordas.android.stockhawk.model.Result;
import com.sam_chordas.android.stockhawk.retrofit_interface.StockHistory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jhani on 7/8/2016.
 */
public class DetailGraphActivity extends AppCompatActivity {

    public static String quote_symbol;
    private static final String BASE_URL = "https://query.yahooapis.com/";
    private Call<Result> mStockHistory;
    private Result result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_line_graph);
        Intent intent = getIntent();
        if (intent != null ) {
            quote_symbol = intent.getStringExtra("symbol");
            Log.v("symbol", quote_symbol);
            getSupportActionBar().setTitle(quote_symbol);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Result.class, new QuoteDserielizer())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            StockHistory stockHistory = retrofit.create(StockHistory.class);
            String startDate = "2015-06-10";
            String endDate = "2016-06-10";
            String q = "select * from yahoo.finance.historicaldata where symbol = \"" + quote_symbol + "\" and startDate = \"" + endDate + "\" and endDate = \"" + startDate + "\"";
            String diagnostics = "true";
            String env = "store://datatables.org/alltableswithkeys";
            String format = "json";
            mStockHistory = stockHistory.getHistoricalData(q, diagnostics, env, format);
            mStockHistory.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    // Get result Repo from response.body()
                    result = response.body();
                    result.toString();
                    Log.v("resultsss value",result.toString());
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    Log.d("MyStocksActivity", t.getMessage());
                }
            });
        }

    }
}
