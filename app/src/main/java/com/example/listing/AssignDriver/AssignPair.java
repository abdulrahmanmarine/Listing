package com.example.listing.AssignDriver;

import com.example.listing.models.Driver;
import com.example.listing.models.Vehicle;

public class AssignPair {
        private Driver driver;
        private Vehicle vehicle;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public AssignPair(Driver driver, Vehicle vehicle) {
        this.driver = driver;
        this.vehicle = vehicle;
    }
}
