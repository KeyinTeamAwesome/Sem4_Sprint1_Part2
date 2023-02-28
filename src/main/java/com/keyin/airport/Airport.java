package com.keyin.airport;

import com.keyin.city.City;
//import com.keyin.aircraft.Aircraft;
//
//import javax.persistence.*;
import java.util.List;


public class Airport {

    private long id;
    private String airportName;
    private String airportCode;
    public Airport() {
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

}