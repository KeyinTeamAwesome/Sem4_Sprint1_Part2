package com.keyin.city;

import com.keyin.airport.Airport;
import com.keyin.passenger.Passenger;


import java.util.List;


public class City {

    private Long id;

    private String name;
    private String state;
    private int population;


    private List<Airport> airports;


    private List<Passenger> passengers;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return name;
    }

    public void setCityName(String name) {
        this.name = name;
    }

    public String getCityState() {
        return state;
    }

    public void setCityState(String state) {
        this.state = state;
    }

    public int getCityPopulation() {
        return population;
    }

    public void setCityPopulation(int population) {
        this.population = population;
    }

    public List<Airport> getAirports() {
        return (List<Airport>) airports;
    }

    public List <Passenger> getPassengers() {
        return (List<Passenger>) passengers;
    }

 public List<Passenger> setPassengers(List <Passenger> passengers) {
        this.passengers = passengers;
     return (List<Passenger>) passengers;
 }

//    public void setAirport(List<Airport> airports) {
//        this.airports = airports;
//    }


}
