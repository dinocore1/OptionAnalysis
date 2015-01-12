package com.devsmart;

import com.google.common.base.Objects;

public class Price {

    public static Price create(double price, int openInterest, int volume) {
        return new Price(price, openInterest, volume);
    }


    public final double price;
    public final int openInterest;
    public final int volume;

    public Price(double price, int openInterest, int volume) {
        this.price = price;
        this.openInterest = openInterest;
        this.volume = volume;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(price, openInterest, volume);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof Price) {
            Price other = (Price)obj;
            return price == other.price
                    && openInterest == other.openInterest
                    && volume == other.volume;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("price=%s, oi=%d, vol=%d", price, openInterest, volume);
    }
}
