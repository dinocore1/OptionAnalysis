package com.devsmart;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Utils {

    private static class OptionPrice {
        Option option;
        Price price;
    }

    public static void saveOptionDb(Database db, File file) throws IOException {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        ArrayList<OptionPrice> priceList = new ArrayList<OptionPrice>(db.callOptions.size());
        for(Map.Entry<Option, Price> priceEntry : db.callOptions.entrySet()){
            OptionPrice optionPrice = new OptionPrice();
            optionPrice.option = priceEntry.getKey();
            optionPrice.price = priceEntry.getValue();
            priceList.add(optionPrice);
        }

        FileWriter fileWriter = new FileWriter(file);
        gson.toJson(priceList, priceList.getClass(), fileWriter);
        fileWriter.close();
    }

    public static void loadOptionDb(Database db, File file) throws IOException
    {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();


        FileReader reader = new FileReader(file);
        OptionPrice[] priceList = gson.fromJson(reader, OptionPrice[].class);
        for(OptionPrice optionPrice : priceList) {
            db.callOptions.put(optionPrice.option, optionPrice.price);
        }
    }}
