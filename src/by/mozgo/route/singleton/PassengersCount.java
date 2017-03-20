package by.mozgo.route.singleton;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Andrei Mozgo
 */
public class PassengersCount {
    private static PassengersCount instance = null;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicInteger passengersCount = new AtomicInteger();

    private PassengersCount() {
    }

    public static PassengersCount getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new PassengersCount();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public AtomicInteger getPassengersCount() {
        return passengersCount;
    }

    public void addPassengers(int passengers) {
        passengersCount.addAndGet(passengers);
    }
}
