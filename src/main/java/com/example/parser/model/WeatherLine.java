package com.example.parser.model;

import org.hibernate.annotations.GenericGenerator;
import org.jsoup.select.Elements;


import javax.persistence.*;

import static com.example.parser.service.ParserService.HEADER_FORMAT;

@Entity
public class WeatherLine {
    public WeatherLine(
            String date,
            String conditions,
            String temp,
            String wind,
            Integer humidity,
            Integer dewPoint,
            Double pressure,
            Integer visibility) {
        this.date = date;
        this.conditions = conditions;
        this.temp = temp;
        this.wind = wind;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.pressure = pressure;
        this.visibility = visibility;
    }

    public WeatherLine() {
    }

    public WeatherLine(String date, Elements siteDataLine) {
        this.date = date + " " + siteDataLine.get(0).text(); // used as a key
        this.conditions = siteDataLine.get(1).text();
        this.temp = siteDataLine.get(2).text();
        this.wind = siteDataLine.get(4).text();
        this.humidity = Integer.parseInt(siteDataLine.get(8).text());
        this.dewPoint = Integer.parseInt(siteDataLine.get(9).text());
        this.pressure = Double.parseDouble(siteDataLine.get(11).text());
        this.visibility = Integer.parseInt(siteDataLine.get(13).text());
    }


    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String date;
    private String conditions;
    private String temp;
    private String wind;
    private Integer humidity;
    private Integer dewPoint;
    private Double pressure;
    private Integer visibility;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Integer dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public String toString() {
        return String.format(HEADER_FORMAT,
                date, conditions, temp, wind, humidity, dewPoint, pressure, visibility);
    }
}