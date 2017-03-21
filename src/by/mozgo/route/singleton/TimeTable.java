package by.mozgo.route.singleton;

import by.mozgo.route.entity.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Andrei Mozgo
 */
public class TimeTable {
    private static TimeTable instance = null;
    private static ReentrantLock lock = new ReentrantLock();
    private static List<Route> routes = new ArrayList<>();

    private TimeTable() {
    }

    public static TimeTable getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new TimeTable();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }

    public void addRoute(Route newRoute) {
        routes.add(newRoute);
    }

    public int getSize() {
        return routes.size();
    }

    public Route getRoute() {
        Route route = null;
        lock.lock();
        if (getSize() > 0) {
            route = routes.get(0);
            routes.remove(route);
        }
        lock.unlock();
        return route;
    }
}
