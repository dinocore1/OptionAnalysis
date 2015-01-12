package com.devsmart;

import com.google.auto.value.AutoValue;

import java.util.Calendar;
import java.util.Date;

@AutoValue
public abstract class Option {

    public static Option create(String symbol, Date expiration, double strike) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiration);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return create(symbol, strike, year, month, day);
    }

    public static Option create(String symbol, double strike, int year, int month, int day) {
        return new AutoValue_Option(symbol.toUpperCase(), strike, year, month, day);
    }


    public abstract String symbol();
    public abstract double strike();
    public abstract int expirationYear();
    public abstract int expirationMonth();
    public abstract int expirationDay();

    public int daysUntilExpiration() {
        Calendar start = Calendar.getInstance();

        Calendar end = Calendar.getInstance();
        end.set(expirationYear(), expirationMonth(), expirationDay());

        if(end.before(start)) {
            return -1;
        }

        int count = 0;
        while(!(start.get(Calendar.YEAR) == end.get(Calendar.YEAR)
                && start.get(Calendar.MONTH) == end.get(Calendar.MONTH)
                && start.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH))){

            final int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);
            if(!(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)){
                count++;
            }

            start.add(Calendar.DAY_OF_MONTH, 1);
        }
        return count;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d/%d/%d", symbol(), strike(), expirationMonth(), expirationDay(), expirationYear());
    }
}
