package com.sam_chordas.android.stockhawk.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sam_chordas.android.stockhawk.model.Result;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by jhani on 7/8/2016.
 */
public class QuoteDserielizer implements JsonDeserializer<JsonArray> {

    ArrayList<Result.StockItem> ResultArray = new ArrayList<>();
    @Override
    public  Result deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject queryObject = json
                .getAsJsonObject()
                .get("query")
                .getAsJsonObject();

        JsonElement quoteElement = queryObject
                .get("results")
                .getAsJsonObject()
                .get("quote");
        JsonArray quoteArray = quoteElement.getAsJsonArray();

        for (int i = 0; i < quoteArray.size(); i++) {
            final JsonElement stockItemElement = quoteArray.get(i);

            final JsonElement dateElement = stockItemElement.getAsJsonObject().get("Date");
            final JsonElement closeElement = stockItemElement.getAsJsonObject().get("Close");
            final String date = dateElement.getAsString();
            final String close = closeElement.getAsString();

            final Result.StockItem  stockItem = new Result.StockItem(date,close);
            ResultArray.add(stockItem);

        }


    Result result = new Result();
    result.setStockItems(ResultArray);


        return result;
    }
}
