package com.devsmart;


import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

public class OptionDownloader {

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
                .withArgName("outfile")
                .hasArg()
                .withDescription("the output database file")
                .create("o"));

        CommandLineParser parser = new BasicParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String symbol = cmd.getOptionValue("s");

            String outputFile = cmd.getOptionValue("o", String.format("%s_options.db", symbol));

            Database db = new Database();
            GoogleOptionDownload downloader = new GoogleOptionDownload(db, symbol);

            try {
                downloader.download();
                Utils.saveOptionDb(db, new File(outputFile));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }


        } catch (ParseException e) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("OptionDownloader", options);
            System.exit(1);
        }
    }
}
