package com.devsmart;


import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;

public class GoogleOptionDownloadTest {

    static class OptionValue {
        Option option;
        Price price;

        double getValue() {
            final int daysLeft = option.daysUntilExpiration();
            return price.price() / daysLeft;
        }
    }

    Comparator<OptionValue> bestPrice = new Comparator<OptionValue>() {

        @Override
        public int compare(OptionValue o1, OptionValue o2) {
            int retval = Double.compare(o2.getValue(), o1.getValue());
            return retval;
        }
    };

    @Test
    public void testDownload() throws IOException {

        Database db = new Database();

        GoogleOptionDownload downloader = new GoogleOptionDownload(db, "SUNE");
        downloader.download();

        assertNotNull(downloader.mExpriationsAvailable);

        LinkedList<OptionValue> values = new LinkedList<OptionValue>();

        for(Map.Entry<Option, Price> entry : db.callOptions.entrySet()) {
            OptionValue ov = new OptionValue();
            ov.option = entry.getKey();
            ov.price = entry.getValue();
            values.add(ov);
        }

        Collections.sort(values, bestPrice);

        for(OptionValue ov : values) {
            System.out.println(String.format("%s %s %s", ov.option, ov.price, ov.getValue()));
        }

    }

}
