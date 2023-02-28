package com.keyin.city;

import com.keyin.data.Database;
import com.keyin.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;

public class CityManager {

        public CityData cityData;

        public List<City> getAllCities() {
            return cityData.getAllCities();
        }

        public City findMatchingCity(City cityToFind){
            List<City> cityList = cityData.getAllCities();
            for (City city : cityList) {
                if (city.equals(cityToFind)){
                    return city;
                }
            }
            return null;
        }

        public CityData getCityData() {
            return cityData;
        }

        public void setCityData(CityData cityData) {
            this.cityData = cityData;
        }
}
