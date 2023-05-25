package com.example.parser.service;

import com.example.parser.model.WeatherLine;
import com.example.parser.repository.WeatherLineRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParserService {
    public static final String HEADER_FORMAT = "%-24s%-20s%-17s%-18s%-14s%-16s%-18s%s";
    private final WeatherLineRepository repository;

    public ParserService(@Autowired WeatherLineRepository weatherLineRepository) {
        this.repository = weatherLineRepository;
    }

    @Transactional
    public void parser() throws Exception {

        printHeader();
        List<WeatherLine> weatherValues = printValues(getPage().selectFirst("table[id=past24Table]").selectFirst("tbody").select("tr"));
        repository.saveAll(weatherValues);
    }

    private void printHeader() {
        System.out.printf(HEADER_FORMAT, "Date/Time (PDT)", "Conditions", "Temp (°C)", "Wind (km/h)", "Humidity (%)", "DewPoint (°C)", "Pressure (kPa)", "Visibility (km)");
        System.out.println();
    }
    private Document getPage() throws IOException {
        String url = "https://weather.gc.ca/past_conditions/index_e.html?station=yvr";
        Document page = Jsoup.parse(new URL(url), 3000);
        if (page != null) {
            return page;
        }
        throw new IOException("Page is null.");
    }

    private List<WeatherLine> printValues(Elements values) {

        List<WeatherLine> weatherLineList = new ArrayList<>();

        String date = "";
        for (int i = 0; i < values.size(); i++) {
            Element valueLine = values.get(i);
            if (valueLine.toString().contains("wxo")) {
                date = valueLine.select(".wxo-th-bkg.table-date").text();
                continue;
            }
            try {
                WeatherLine line = new WeatherLine(date, valueLine.select("td"));
                weatherLineList.add(line);
                System.out.println(line);
            } catch (NumberFormatException e) {
                // Handling a String to Number Error
                e.printStackTrace();
                // Or setting default values
            }
        }
        return weatherLineList;
    }
}