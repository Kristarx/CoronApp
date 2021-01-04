package com.example.coronapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadFromFile
{
    private Map<String, Integer> returnReadData = new LinkedHashMap<>();

    public Map<String, Integer> readFile(String file, String country)
    {
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.contains(country))
                {

                    String date = myReader.nextLine().substring(6);
                    String value = myReader.nextLine().substring(7);
                    String dateArray[] = date.split(",");
                    String valueArray[] = value.split(",");

                    for(int i = 0; i< dateArray.length; i++)
                    {
                        returnReadData.put(dateArray[i], Integer.parseInt(valueArray[i]));
                    }

                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returnReadData;
    }
}
