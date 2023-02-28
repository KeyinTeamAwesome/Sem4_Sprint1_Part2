package com.airport;

import com.keyin.airport.Airport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


public class AirportTest {

        @Mock
        private Airport airport;

        @Test
        public void testAirport() {
            List<Airport> airportList = new ArrayList<Airport>();
            airportList.add(airport);
            when(airport.getAirportName()).thenReturn("JFK");
            Assertions.assertEquals("JFK", airportList.get(0).getAirportName());
        }
}
