package com.sam_chordas.android.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sam_chordas.android.stockhawk.model.QuoteDserielizer;
import com.sam_chordas.android.stockhawk.model.Stock;
import com.sam_chordas.android.stockhawk.retrofit_interface.StockHistory;

import java.lang.reflect.Type;
import java.util.List;

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
    private Call<List<Stock>> mStockHistory;
    private Stock stock;
    private List<Stock> mStockItemList;
    private int dataSetSize;


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
                    .registerTypeAdapter(Stock.class, new QuoteDserielizer())
                    .create();
            Type listType = new TypeToken<List<Stock>>() {}.getType();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().registerTypeAdapter(
                            listType, new QuoteDserielizer()).create()))
                    .build();

            StockHistory stockHistory = retrofit.create(StockHistory.class);
            String startDate = "2015-06-10";
            String endDate = "2016-06-10";

            String query = "select * from yahoo.finance.historicaldata where symbol = \'" + quote_symbol + "\' and startDate = \'" + startDate + "\' and endDate = \'" + endDate + "\'";
            mStockHistory = stockHistory.getHistoricalData(query);
            mStockHistory.enqueue(new Callback<List<Stock>>() {
                @Override
                public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {

                    Log.d("response.raw", response.raw().toString());

                    Log.d("result",call.toString());
                    Log.d("result:::",response.toString());
                    mStockItemList = response.body();
                        dataSetSize = mStockItemList.size();




                }

                @Override
                public void onFailure(Call<List<Stock>> call, Throwable t) {

                }


            });
        }

    }
}
