package com.devsmart;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class GoogleOptionDownload {

    private final String mSymbol;
    private final Database mDb;
    private Gson gson = new GsonBuilder().create();
    Expiration[] mExpriationsAvailable;

    public static class ReturnObj {
        Expiration expiry;
        Expiration[] expirations;
        GOption[] puts;
        GOption[] calls;
        double underlying_price;
    }

    public static class Expiration {
        int y;
        int m;
        int d;
    }

    public static class GOption {
        String cid;
        String a;
        String b;
        String oi;
        String vol;
        String strike;
        String expiry;
        String p;

    }

    public GoogleOptionDownload(Database db, String symbol) {
        mDb = db;
        mSymbol = symbol;
    }

    public void download() throws IOException {

        String url = String.format("http://www.google.com/finance/option_chain?q=%s&output=json", mSymbol);
        String response = Request.Get(url)
                .execute()
                .returnContent().asString();


        ReturnObj returnObj = gson.fromJson(response, ReturnObj.class);
        mExpriationsAvailable = returnObj.expirations;

        addData(returnObj.calls, returnObj.expiry);

        for(Expiration expr : returnObj.expirations) {
            download(expr);
        }

    }

    public void download(Expiration expiration) throws IOException {
        String url = String.format("http://www.google.com/finance/option_chain?q=%s&expd=%s&expm=%s&expy=%s&output=json",
                mSymbol, expiration.d, expiration.m, expiration.y);
        String response = Request.Get(url)
                .execute()
                .returnContent().asString();

        ReturnObj returnObj = gson.fromJson(response, ReturnObj.class);

        addData(returnObj.calls, expiration);
    }

    private void addData(GOption[] options, Expiration expiry) {
        for(GOption option : options) {
            try {
                double strike = Double.parseDouble(option.strike);
                Option key = Option.create(mSymbol, strike, expiry.y, expiry.m, expiry.d);
                Price price = Price.create(Double.parseDouble(option.p),
                        Integer.parseInt(option.oi),
                        Integer.parseInt(option.vol)
                );
                mDb.callOptions.put(key, price);
            } catch (NumberFormatException e) {}
        }
    }


}
