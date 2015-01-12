package com.devsmart;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {


    public static void saveOptionDb(Database db, File file) throws IOException {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        FileWriter fileWriter = new FileWriter(file);
        gson.toJson(db.callOptions, db.callOptions.getClass(), fileWriter);
        fileWriter.close();

    }
}
