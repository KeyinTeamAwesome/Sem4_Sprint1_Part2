package com.keyin.airport;



import java.util.List;

public class AirportManager {

    public AirportData airportData;

    public List<Airport> getAllAirport() {
        return airportData.getAllAirports();
    }

    public Airport findMatchingAircraft(Airport airportToFind){
        List<Airport> airportList = airportData.getAllAirports();
        for (Airport airport : airportList) {
            if (airport.equals(airportToFind)){
                return airport;
            }
        }
        return null;
    }

    public AirportData getAirportData() {
        return airportData;
    }

    public void setAirportData(AirportData airportData) {
        this.airportData = airportData;
    }

}