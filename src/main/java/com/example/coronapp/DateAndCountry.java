package com.example.coronapp;

public class DateAndCountry
{
    public DateAndCountry() {
    }

    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DateAndCountry(String country, String model) {
        this.country = country;
    }
}
