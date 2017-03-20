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

    public void exchangePassengers(Bus bus) throws InterruptedException {
        LOGGER.log(Level.INFO, "{}: Bus" + bus.getId() + " reached stop ", name);
        semaphore.acquire();
        LOGGER.log(Level.INFO, "{}: Bus" + bus.getId() + " acquired semaphore", name);
        busStopLock.lock();
        stoppedBuses.add(bus);
        LOGGER.log(Level.INFO, "{}: BusStop locked. Passengers on stop: {}. Buses on stop: {}",
                name, passengersOnStop, stoppedBuses.size());
        for (Bus stoppedBus : stoppedBuses) {
            LOGGER.log(Level.INFO, "{}. Passengers in bus{}: {}", name, stoppedBus.getId(), stoppedBus.getPassengers());
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
        LOGGER.log(Level.INFO, "{}: BusStop before unlock. Passengers on stop: {}. Buses on stop: {}",
                name, passengersOnStop, stoppedBuses.size());
        for (Bus stoppedBus : stoppedBuses) {
            LOGGER.log(Level.INFO, "{}. Passengers in bus{}: {}", name, stoppedBus.getId(), stoppedBus.getPassengers());
        }
        busStopLock.unlock();
        TimeUnit.SECONDS.sleep(1);
        stoppedBuses.remove(bus);
        LOGGER.log(Level.INFO, "{}. Bus{} leaved stop.", name, bus.getId());
        semaphore.release();
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
