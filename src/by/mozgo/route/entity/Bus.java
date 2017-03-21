package by.mozgo.route.entity;

import by.mozgo.route.singleton.PassengersCount;
import by.mozgo.route.singleton.TimeTable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Andrei Mozgo
 */
public class Bus extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();
    private int passengers;
    private Route route;

    public Bus() {
    }

    public void run() {
        route = TimeTable.getInstance().getRoute();
        for (BusStop busStop : route.getBusStops()) {
            try {
                busStop.exchangePassengers(this);
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "InterruptedException in bus {} on stop {}. ", getNumber(), busStop.getName(), e);
            }
        }
    }

    public long getNumber() {
        return route.getNumber();
    }

    public int getPassengers() {
        return passengers;
    }

    public void addPassengers(int newPassengers) {
        passengers += newPassengers;
        PassengersCount.getInstance().addPassengers(newPassengers);
    }

    public void exitPassengers(int exitedPassengers) {
        passengers -= exitedPassengers;
    }

    @Override
    public String toString() {
        return route.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() == this.getClass()) {
            Bus bus = (Bus) obj;
            if (bus.passengers == this.passengers &&
                    bus.route.equals(this.route)) {
                return true;
            }
        }
        return false;
    }
}
