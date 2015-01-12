package com.devsmart;


import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;

public class OptionBestValue {

    static class OptionValue {
        Option option;
        Price price;

        double getAssignValue(double avgPrice) {
            return option.strike + price.price - avgPrice;
        }

        double getValue(double avgPrice) {
            final int daysLeft = option.daysUntilExpiration();

            double value = getAssignValue(avgPrice);

            return value / daysLeft;
        }
    }



    public static void main(String[] args) {

        Options options = new Options();

        options.addOption(OptionBuilder
                .withArgName("symbol")
                .hasArg()
                .isRequired()
                .withLongOpt("symbol")
                .withDescription("the stock symbol ticker to download")
                .create("s"));

        options.addOption(OptionBuilder
                .withArgName("dbfile")
                .hasArg()
                .isRequired()
                .withDescription("the input database file")
                .create("i"));

        options.addOption(OptionBuilder
                .hasArg()
                .withArgName("underline price")
                .withDescription("the avg price of the owned stock")
                .isRequired()
                .create("p"));

        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            final String symbol = cmd.getOptionValue("s");

            File inputfile = new File(cmd.getOptionValue("i"));

            Database db = new Database();

            try {
                System.out.println("Loading file: " + inputfile.getAbsolutePath());
                Utils.loadOptionDb(db, inputfile);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }


            final double price = Double.parseDouble(cmd.getOptionValue("p"));

            final Comparator<OptionValue> bestPrice = new Comparator<OptionValue>() {

                @Override
                public int compare(OptionValue o1, OptionValue o2) {
                    int retval = Double.compare(o2.getValue(price), o1.getValue(price));
                    return retval;
                }
            };

            LinkedList<OptionValue> values = new LinkedList<OptionValue>();

            for(Map.Entry<Option, Price> entry : db.callOptions.entrySet()) {
                OptionValue ov = new OptionValue();
                ov.option = entry.getKey();
                ov.price = entry.getValue();
                values.add(ov);
            }

            Collections.sort(values, bestPrice);

            for(OptionValue ov : values) {
                System.out.println(String.format("%s %s %s", ov.option, ov.price, ov.getValue(price)));
            }

        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("OptionBestValue", options);
            System.exit(1);
        }
    }
}
