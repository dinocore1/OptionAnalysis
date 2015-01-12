package com.devsmart;


import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Price {

    public static Price create(double price, int openInterest, int volume) {
        return new AutoValue_Price(price, openInterest, volume);
    }

    public abstract double price();
    public abstract int openInterest();
    public abstract int volume();
}
