package com.keyin.aircraft;

public class Aircraft {

        private long id;
        private String type;
        private String airlineName;
        private int numberOfPassengers;

        public Aircraft() {
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAirlineName() {
            return airlineName;
        }

        public void setAirlineName(String airlineName) {
            this.airlineName = airlineName;
        }

        public int getNumberOfPassengers() {
            return numberOfPassengers;
        }

        public void setNumberOfPassengers(int numberOfPassengers) {
            this.numberOfPassengers = numberOfPassengers;
        }
}
