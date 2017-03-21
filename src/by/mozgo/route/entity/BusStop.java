package by.mozgo.route.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Andrei Mozgo
 */
public class BusStop {

    private static final Logger LOGGER = LogManager.getLogger();
    private BusStopName name;
    private Semaphore semaphore;
    private List<Bus> stoppedBuses = new ArrayList<>();
    private int passengersOnStop;
    private ReentrantLock busStopLock = new ReentrantLock(true);

    public BusStop(BusStopName name, int capacity, int passengersOnStop) {
        this.name = name;
        this.semaphore = new Semaphore(capacity, true);
        this.passengersOnStop = passengersOnStop;
    }

    public BusStopName getName() {
        return name;
    }

    public int getPassengersOnStop() {
        return passengersOnStop;
    }

    public void exchangePassengers(Bus bus) throws InterruptedException {
        LOGGER.log(Level.INFO, "{}: Bus" + bus.getNumber() + " reached stop and wait. ", name);
        semaphore.acquire();
        LOGGER.log(Level.INFO, "{}: Bus" + bus.getNumber() + " opens doors (acquired semaphore)", name);
        busStopLock.lock();
        stoppedBuses.add(bus);
        LOGGER.log(Level.INFO, "{}: Bus{} starts exchange. Passengers on stop: {}. {} buses on stop: {}",
                name, bus.getNumber(), passengersOnStop, stoppedBuses.size(), getStoppedBusesNumbers());
        for (Bus stoppedBus : stoppedBuses) {
            LOGGER.log(Level.INFO, "{}: Passengers in bus{}: {}", name, stoppedBus.getNumber(), stoppedBus.getPassengers());
        }
        int passengersLeaveBus = (int) Math.round(Math.random() * bus.getPassengers());
        int passengersEnterBus = (int) Math.round(Math.random() * passengersOnStop);
        bus.exitPassengers(passengersLeaveBus);
        for (Bus stoppedBus : stoppedBuses) {
            int passengersEnterAnotherBus = (int) Math.round(Math.random() * passengersLeaveBus);
            stoppedBus.addPassengers(passengersEnterAnotherBus);
            passengersLeaveBus -= passengersEnterAnotherBus;
        }
        passengersOnStop = passengersOnStop - passengersEnterBus + passengersLeaveBus;
        bus.addPassengers(passengersEnterBus);
        LOGGER.log(Level.INFO, "{}: Bus{} has ended exchange. Passengers on stop: {}. {} buses on stop: {}",
                name, bus.getNumber(), passengersOnStop, stoppedBuses.size(), getStoppedBusesNumbers());
        for (Bus stoppedBus : stoppedBuses) {
            LOGGER.log(Level.INFO, "{}: Passengers in bus{}: {}", name, stoppedBus.getNumber(), stoppedBus.getPassengers());
        }
        busStopLock.unlock();
        TimeUnit.SECONDS.sleep(1);
        busStopLock.lock();
        stoppedBuses.remove(bus);
        busStopLock.unlock();
        LOGGER.log(Level.INFO, "{}. Bus{} has closed doors and leaves stop.", name, bus.getNumber());
        semaphore.release();
    }

    private StringBuilder getStoppedBusesNumbers() {
        StringBuilder numbers = new StringBuilder();
        for (Bus bus : stoppedBuses) {
            numbers.append(bus.getNumber());
            numbers.append(" ");
        }
        return numbers;
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() == this.getClass()) {
            BusStop busStop = (BusStop) obj;
            if (busStop.name.equals(this.name) && busStop.passengersOnStop == this.passengersOnStop &&
                    busStop.stoppedBuses.equals(this.stoppedBuses)) {
                return true;
            }
        }
        return false;
    }
}
