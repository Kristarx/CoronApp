package com.example.coronapp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


@Controller
public class Navigation
{
    Map<String, Integer> readData = new LinkedHashMap<>();

    public static int tab = 0;
    private String chosenCountry;
    private CSVDataParser dataParser;
    private int checkIfRedirectChart = 0;
    private int checkIfRedirectCases = 0;


    public Navigation(CSVDataParser dataParser)
    {
        this.dataParser = dataParser;
        chosenCountry = "(please select country from list)";
        readData.put("", 0);
    }

    public void setTab(int value)
    {
        tab = value;
    }


    @GetMapping("/cases_of_recovery")
    public String getCasesOfRecovery(@RequestParam(name = "cases", defaultValue = "Wrong data")String cases, Model model) throws IOException {
        setTab(2);
        if(checkIfRedirectCases != 0)
        {
            dataParser.setDate("1/22/20");
        }
        checkIfRedirectCases++;
        dataParser.getDate();
        cases = "Cases of recovery";
        model.addAttribute("cases", cases);
        model.addAttribute("activePage", "cases_of_recovery");
        model.addAttribute("points", dataParser.getCovidData());
        model.addAttribute("ValidReceiveDateForCSV", new ValidReceiveDateForCSV());
        return "cases_of";
    }

    @GetMapping("/cases_of_infections")
    public String getCasesOfInfections(@RequestParam(name = "cases", defaultValue = "Wrong data")String cases, Model model) throws IOException {
        setTab(1);
        if(checkIfRedirectCases != 0)
        {
            dataParser.setDate("1/22/20");
        }
        checkIfRedirectCases++;
        dataParser.getDate();
        cases = "Cases of infections";
        model.addAttribute("cases", cases);
        model.addAttribute("activePage", "cases_of_infections");
        model.addAttribute("points", dataParser.getCovidData());
        model.addAttribute("ValidReceiveDateForCSV", new ValidReceiveDateForCSV());
        return "cases_of";
    }

    @GetMapping("/cases_of_deaths")
    public String getCasesOfDeaths(@RequestParam(name = "cases", defaultValue = "Wrong data")String cases, Model model) throws IOException {
        setTab(3);
        if(checkIfRedirectCases != 0)
        {
            dataParser.setDate("1/22/20");
        }
        checkIfRedirectCases++;
        dataParser.getDate();
        cases = "Cases of deaths";
        model.addAttribute("cases", cases);
        model.addAttribute("activePage", "cases_of_deaths");
        model.addAttribute("points", dataParser.getCovidData());
        model.addAttribute("ValidReceiveDateForCSV", new ValidReceiveDateForCSV());
        return "cases_of";
    }

    @GetMapping("/charts")
    public String getCharts(Model model){


        model.addAttribute("activePage", "charts");

        ReadFromFile read = new ReadFromFile();

        readData = read.readFile("src/main/resources/Files/InfectionsSum.txt", chosenCountry.trim());

        if(checkIfRedirectChart != 0 || chosenCountry.equals("--Select country--"))
        {
            chosenCountry = "(please select country from list)";
        }

        checkIfRedirectChart++;

        model.addAttribute("country", chosenCountry);
        model.addAttribute("surveyMap", readData);
        model.addAttribute("DateAndCountry", new DateAndCountry());

        return "charts";
    }

    @PostMapping("/chooseCountryCharts")
    public String addCar(@ModelAttribute DateAndCountry dac) {
        chosenCountry = dac.getCountry();
        checkIfRedirectChart = 0;
        return "redirect:/charts";
    }

    @PostMapping("/validDate")
    public String getDataToLoadMarker(@ModelAttribute ValidReceiveDateForCSV valid)
    {
        if(!(valid.getYear().equals("--Select year--") || valid.getMonth().equals("--Select month--") || valid.getYear() == null || valid.getMonth() == null || valid.getDay() == null))
        {
            dataParser.setDate(valid.returnValidateDate(valid.getDay(), valid.getMonth(), valid.getYear()));
            checkIfRedirectCases = 0;

        }

        if(tab == 1)
        {
            return "redirect:/cases_of_infections";
        }
        else if (tab == 2)
        {
            return "redirect:/cases_of_recovery";
        }
        else if (tab == 3)
        {
            return "redirect:/cases_of_deaths";
        }
        else
        {
            return "redirect:";
        }
    }
}


