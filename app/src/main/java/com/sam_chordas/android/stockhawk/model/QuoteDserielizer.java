package com.sam_chordas.android.stockhawk.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhani on 7/8/2016.
 */
public class QuoteDserielizer implements JsonDeserializer<List<Stock>> {

    List<Stock> stockArrayList = new ArrayList<>();

    @Override
    public List<Stock> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        return new Gson().fromJson(

                json
                        .getAsJsonObject().get("query")
                        .getAsJsonObject().get("results")
                        .getAsJsonObject().get("quote")
                        .getAsJsonArray(), typeOfT);
    }

}
