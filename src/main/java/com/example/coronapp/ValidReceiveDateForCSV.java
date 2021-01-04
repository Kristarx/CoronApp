package com.example.coronapp;

public class ValidReceiveDateForCSV
{
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String year;
    private String month;
    private String day;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String returnValidateDate(String day, String month, String year)
    {
        String validateDate;
        int monthNumber = 0;
        for(int i = 0; i < months.length; i++)
        {
            if(months[i].equals(month))
            {
                monthNumber = i+1;
            }
        }
        validateDate = monthNumber + "/" + day + "/" + year.substring(2);
        return validateDate;
    }
}
