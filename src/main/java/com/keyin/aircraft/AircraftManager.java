package com.keyin.aircraft;

import java.util.List;
import java.util.ArrayList;

public class AircraftManager {

    private AircraftData aircraftData;

    public List<Aircraft> getAllAircraft() {
        return aircraftData.getAllAircraft();
    }

    public Aircraft findMatchingAircraft(Aircraft aircraftToFind){
        List<Aircraft> aircraftList = aircraftData.getAllAircraft();
        for (Aircraft aircraft : aircraftList) {
            if (aircraft.equals(aircraftToFind)){
                return aircraft;
            }
            }
        return null;
    }

    public AircraftData getAircraftData() {
        return aircraftData;
    }

    public void setAircraftData(AircraftData aircraftData) {
        this.aircraftData = aircraftData;
    }

}
