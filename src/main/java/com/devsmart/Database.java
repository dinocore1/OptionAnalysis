package com.devsmart;


import com.google.common.collect.ComparisonChain;

import java.util.Comparator;
import java.util.TreeMap;

public class Database {

    TreeMap<Option, Price> callOptions = new TreeMap<Option, Price>(optionComparator);

    public static final Comparator<Option> optionComparator = new Comparator<Option>() {
        @Override
        public int compare(Option o1, Option o2) {
            return ComparisonChain.start()
                    .compare(o1.symbol(), o2.symbol())
                    .compare(o1.strike(), o2.strike())
                    .compare(o1.expirationYear(), o2.expirationYear())
                    .compare(o1.expirationMonth(), o2.expirationMonth())
                    .compare(o1.expirationDay(), o2.expirationDay())
                    .result();
        }
    };

    public void setCallOptionPrice(Option option, Price price) {
        callOptions.put(option, price);
    }


}
