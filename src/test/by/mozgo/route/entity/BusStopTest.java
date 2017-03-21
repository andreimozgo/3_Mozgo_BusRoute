package test.by.mozgo.route.entity;

import by.mozgo.route.entity.Bus;
import by.mozgo.route.entity.BusStop;
import by.mozgo.route.entity.BusStopName;
import by.mozgo.route.entity.Route;
import by.mozgo.route.singleton.TimeTable;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrei Mozgo
 */
public class BusStopTest {
    @Test
    public void testExchangePassengers() {
        BusStop busStop = new BusStop(BusStopName.STOP1, 2, 20);
        List<BusStop> busStops = new ArrayList<>();
        Route route = new Route(1, busStops);
        TimeTable.getInstance().addRoute(route);
        Bus bus = new Bus();
        int totalPassengers = bus.getPassengers() + busStop.getPassengersOnStop();
        try {
            busStop.exchangePassengers(bus);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int totalPassengersAfterExchange = bus.getPassengers() + busStop.getPassengersOnStop();
        Assert.assertEquals(totalPassengers, totalPassengersAfterExchange);
    }
}
