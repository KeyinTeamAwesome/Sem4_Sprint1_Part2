package com.city;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import com.keyin.city.City;

public class CityTest {

    @Mock
    private City city;

    @Test
    public void testCity() {
        List<City> cityList = new ArrayList<City>();
        cityList.add(city);
        when(city.getCityName()).thenReturn("New York");
        Assertions.assertEquals("New York", cityList.get(0).getCityName());
    }

}
