package com.keyin.cities;

import com.keyin.city.City;
import com.keyin.data.Database;
import com.keyin.passenger.Passenger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CityManagerTest {
    @Mock
    private Database mockDatabase;

    @Test
    public void testGetCitiesByPassengerName() {
        Passenger stJohns = new Passenger();
        stJohns.setId(1);
        stJohns.setFirstName("St. John's");

        Passenger gander = new Passenger();
        gander.setId(2);
        gander.setName("Gander");

        List<City> cityList = new ArrayList<City>();

        City stJohnCity = new City();
        stJohnCity.setId(1);
        stJohnCity.setPassenger(stJohns);
        stJohnCity.setCode("YYT");

        cityList.add(stJohnCity);

        Mockito.when(mockDatabase.getPassengerByName("St. John's")).thenReturn(stJohns);
        Mockito.when(mockDatabase.getPassengerByName("Gander")).thenReturn(gander);
        Mockito.when(mockDatabase.getAllCities()).thenReturn(cityList);

        CityManager cityManagerUnderTest = new CityManager();

        cityManagerUnderTest.setDatabase(mockDatabase);

        Assertions.assertEquals(1, cityManagerUnderTest.getCitiesByPassengerName("St. John's").size());

        Assertions.assertEquals(0, cityManagerUnderTest.getCitiesByPassengerName("Gander").size());

        City ganderCity = new City();
        ganderCity.setId(2);
        ganderCity.setPassenger(gander);
        ganderCity.setCode("YQX");

        cityList.add(ganderCity);

        Assertions.assertEquals(1, cityManagerUnderTest.getCitiesByPassengerName("Gander").size());
        Assertions.assertEquals(1, cityManagerUnderTest.getCitiesByPassengerName("St. John's").size());
    }

}
