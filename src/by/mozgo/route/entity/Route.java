package by.mozgo.route.entity;

import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class Route {
    private int number;
    private List<BusStop> busStops;

    public Route(int number, List<BusStop> busStops) {
        this.number = number;
        this.busStops = busStops;
    }

    public int getNumber() {
        return number;
    }

    public List<BusStop> getBusStops() {
        return busStops;
    }

    @Override
    public String toString() {
        return number + busStops.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() == this.getClass()) {
            Route route = (Route) obj;
            if (route.number == this.number &&
                    route.busStops.equals(this.busStops)) {
                return true;
            }
        }
        return false;
    }
}
