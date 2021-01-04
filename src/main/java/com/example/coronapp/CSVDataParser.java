package com.example.coronapp;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVDataParser {

    CSVDataParser()
    {
        this.date = "1/22/20";
    }
    private String url = "";
    private String presentCase = "";
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    private void writeURL()
    {
        if(Navigation.tab == 1)
        {
            url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
            presentCase = "disease: ";
        }
        else if(Navigation.tab == 2)
        {
            url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_recovered_global.csv";
            presentCase = "recovered: ";
        }
        else
        {
            url = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_deaths_global.csv";
            presentCase = "deaths: ";
        }
    }

    public List<Marker> getCovidData() throws IOException {

        writeURL();

        List <Marker> marker = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String values = restTemplate.getForObject(url, String.class);

        StringReader stringReader = new StringReader(values);
        CSVParser parse = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);


        for (CSVRecord strings : parse)
        {
            if((strings.get("Lat") == null || !strings.get("Lat").equals("")) || (strings.get("Long") == null || !strings.get("Long").equals("")))
            //ten if to proste zapobieganie pustym wartościom lat i long bo jak się okazywało w trakcie testów jakimś cudem takie wartości były XD
            //docelowo powinniśmy to obsłużyć jakimś prostym wyjątkiem
            {
                //System.out.println(strings);
                double lat = Double.parseDouble(strings.get("Lat"));
                double lon = Double.parseDouble(strings.get("Long"));
                String country_Region;
                String text;

                if(strings.get("Province/State").equals("") || strings.get("Province/State") == null)
                {
                    country_Region = strings.get("Country/Region");

                    text = "Confirmed " + presentCase + strings.get(date) + "<br>Country: " + country_Region +"<br>Date: " + date;
                }
                else
                {
                    country_Region = strings.get("Province/State") + "/" + strings.get("Country/Region");
                    text = "Confirmed " + presentCase + strings.get(date) + "<br>Province/Country: " + country_Region + "<br>Date: " + date;
                }

                marker.add(new Marker(lat, lon, text));
            }

        }

        return marker;

    }
}
