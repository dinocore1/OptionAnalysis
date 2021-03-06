package com.devsmart;


import com.google.common.base.Objects;

import java.util.Calendar;
import java.util.Date;

public class Option {

    public static Option create(String symbol, Date expiration, double strike) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiration);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return create(symbol, strike, year, month, day);
    }

    public static Option create(String symbol, double strike, int year, int month, int day) {
        return new Option(symbol.toUpperCase(), strike, year, month, day);
    }

    public final String symbol;
    public final double strike;
    public final int expirationYear;
    public final int expirationMonth;
    public final int expirationDay;


    public Option(String symbol, double strike, int year, int month, int day) {
        this.symbol = symbol;
        this.strike = strike;
        this.expirationYear = year;
        this.expirationMonth = month;
        this.expirationDay = day;
    }



    public int daysUntilExpiration() {
        Calendar start = Calendar.getInstance();

        Calendar end = Calendar.getInstance();
        end.set(expirationYear, expirationMonth-1, expirationDay);

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
    public int hashCode() {
        return Objects.hashCode(symbol, strike, expirationYear, expirationMonth, expirationDay);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof Option) {
            Option other = (Option)obj;
            return Objects.equal(symbol, other.symbol)
                    && strike == other.strike
                    && expirationYear == other.expirationYear
                    && expirationMonth == other.expirationMonth
                    && expirationDay == other.expirationDay;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %d/%d/%d", symbol, strike, expirationMonth, expirationDay, expirationYear);
    }
}
