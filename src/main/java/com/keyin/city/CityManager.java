//package com.keyin.city;//package com.keyin.passenger;
//
//import com.keyin.data.Database;
//import com.keyin.passenger.Passenger;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CityManager {
//    private Database database;
//    // Used to be List<Airport> getAirportsByCityName(String cityName)
//    public List<City> getCitiesByPassengerName(String passengerName) {
//        Passenger passenger = database.getPassengerByName(passengerName);
//        List<City> cities = database.getAllCities();
//
//        List<City> citiesToReturn = new ArrayList<City>();
//
//        for (City a : cities) {
//            if (a.getPassenger().getName().equals(passenger.getName())) {
//                citiesToReturn.add(a);
//            }
//        }
//
//        return citiesToReturn;
//    }
//
//    public Database getDatabase() {
//        return database;
//    }
//
//    public void setDatabase(Database database) {
//        this.database = database;
//    }
//}
