package by.mozgo.route.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class Bus extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();
    private long id;
    private int passengers;
    private List<BusStop> route;

    public Bus(int id, int passengers, List<BusStop> route) {
        this.id = id;
        this.passengers = passengers;
        this.route = route;
    }

    public void run() {
        for (BusStop busStop : route) {
            try {
                busStop.exchangePassengers(this);
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, "InterruptedException in bus" + id + " on stop " + busStop.getName());
            }
        }
    }

    @Override
    public long getId() {
        return id;
    }

    public int getPassengers() {
        return passengers;
    }

    public void addPassengers(int newPassengers) {
        passengers += newPassengers;
    }

    public void exitPassengers(int exitedPassengers) {
        passengers -= exitedPassengers;
    }

    @Override
    public String toString() {
        return id + " " + passengers + " " + route;
    }
}